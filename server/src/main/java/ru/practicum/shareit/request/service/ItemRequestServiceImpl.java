package ru.practicum.shareit.request.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.dto.ResponseCommentDto;
import ru.practicum.shareit.item.dto.ResponseItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.utills.CommentMapper;
import ru.practicum.shareit.item.utills.ItemMapper;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.GetItemRequestDto;
import ru.practicum.shareit.request.dto.ResponseItemRequestDto;
import ru.practicum.shareit.request.dto.ResponseItemRequestListDto;
import ru.practicum.shareit.request.exception.ItemRequestNotFoundException;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.request.utills.ItemRequestMapper;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

@Slf4j
@Service
public class ItemRequestServiceImpl implements ItemRequestService {

	private final BookingRepository bookingRepository;
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final ItemRepository itemRepository;
	private final ItemRequestRepository itemRequestRepository;

	public ItemRequestServiceImpl(ItemRequestRepository itemRequestRepository, UserRepository userRepository,
								  ItemRepository itemRepository, BookingRepository bookingRepository,
								  CommentRepository commentRepository) {
		this.itemRepository = itemRepository;
		this.userRepository = userRepository;
		this.itemRequestRepository = itemRequestRepository;
		this.commentRepository = commentRepository;
		this.bookingRepository = bookingRepository;
	}

	@Override
	public ResponseItemRequestDto createItemRequest(CreateItemRequestDto createRequestDto) {
		String errorMessge = "Невозможно создать запрос на бронироввание";
		ItemRequest itemRequestToCreate = enrichAndMappingCreateItemRequestDto(createRequestDto, errorMessge);
		ItemRequest createdItemRequest = itemRequestRepository.save(itemRequestToCreate);
		ResponseItemRequestDto responseItemRequestDto = ItemRequestMapper.itemRequestToResponseItemRequestDto(createdItemRequest);
		return responseItemRequestDto;
	}

	@Override
	public ResponseItemRequestDto getItemRequest(GetItemRequestDto getItemRequestDto) {
		String errorMessage = "Невозможно получить запрос на бронирование";
		Long itemRequestId = getItemRequestDto.getItemRequestId();
		ItemRequest itemRequest = getItemRequest(itemRequestId, errorMessage);
		ResponseItemRequestDto responseItemRequestDto = ItemRequestMapper.itemRequestToResponseItemRequestDto(itemRequest);

		Long ownerId = getItemRequestDto.getOwnerId();
		List<ResponseItemDto> itemsList = getItemsOfOwner(ownerId);

		responseItemRequestDto.setItems(itemsList);
		return responseItemRequestDto;
	}

	@Override
	public List<ResponseItemRequestListDto> getItemsByRequestorId(Long requestorId) {
		List<ItemRequest> itemRequestsOfRequestorList = itemRequestRepository.getItemsByRequestorId(requestorId);
		List<Item> itemsList = itemRepository.findRequestedItemsOfUser(requestorId);
		return setItemDtoListsToItemRequests(itemRequestsOfRequestorList, itemsList);
	}

	public List<ResponseItemRequestListDto> getItemsOfUsersExcludingRequestorById(Long requestorId) {
		List<ItemRequest> itemsOfOtherUsersList = itemRequestRepository
				.getItemsOfUsersExcludingRequestorById(requestorId);
		List<Item> itemsList = itemRepository.findRequestedItemsOfOtherUsers(requestorId);
		return setItemDtoListsToItemRequests(itemsOfOtherUsersList, itemsList);
	}

	private List<ResponseItemRequestListDto> setItemDtoListsToItemRequests(List<ItemRequest> itemRequestList,
			List<Item> itemsList) {
		return	itemRequestList.stream()
				.map(ItemRequestMapper::itemRequestToResponseItemRequestListDto)
				.peek(request -> request.setItems(itemsList.stream()
												.filter(item -> item.getItemRequest().getId().equals(request.getId()))
												.map(ItemMapper::itemToItemDto)
												.toList()))
				.toList();
	}

	private ItemRequest getItemRequest(Long itemRequestId, String errorMessage) {
		return	itemRequestRepository.findById(itemRequestId)
				.orElseThrow(() -> new ItemRequestNotFoundException(itemRequestId, errorMessage));
	}

	private ItemRequest enrichAndMappingCreateItemRequestDto(CreateItemRequestDto createItemRequestDto,
			String errorMessage) {
		Long requestorId = createItemRequestDto.getOwnerId();
		User requestor = getUser(requestorId, errorMessage);
		LocalDateTime created = LocalDateTime.now();
		ItemRequest itemRequest = ItemRequestMapper.createItemRequestDtoToItemRequest(createItemRequestDto);
		itemRequest.setRequestor(requestor);
		itemRequest.setCreated(created);
		return itemRequest;
	}

	private User getUser(Long userId, String errorMessage) {
		return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId, errorMessage));
	}

	private List<ResponseItemDto> getItemsOfOwner(Long ownerId) {
		log.info("Начат процесс получения списка предметов владельца. Получен id-владельца=" + ownerId);
		List<Item> responseItemsList = itemRepository.findByOwnerId(ownerId);
		log.info("Получен список предметов владельца" + responseItemsList);

		log.info("Начато преобразование списка ItemCreateDto в список объектов ResponseItemDt. Получен объект:"
				+ responseItemsList);
		List<ResponseItemDto> responseItemsListDto = responseItemsList.stream().map(ItemMapper::itemToResponseItemDto)
				.toList();
		log.info("Закончено преобразование ItemCreateDto в объект ResponseItemDt. Получен объект:"
				+ responseItemsListDto);

		Map<Long, List<Booking>> bookingMap = getBookingsByItems(responseItemsList);

		LocalDateTime now = LocalDateTime.now();

		for (int i = 0; i < responseItemsListDto.size(); i++) {
			ResponseItemDto itemDto = responseItemsListDto.get(i);
			Long itemDtoId = itemDto.getId();

			List<Booking> bookingList = bookingMap.get(itemDtoId) == null ? Collections.emptyList()
					: bookingMap.get(itemDtoId);
			List<Booking> bookingListSorted = bookingList.stream().sorted(Comparator.comparing(Booking::getStart))
					.toList();

			Booking nextBooking = bookingListSorted.stream().filter(booking -> booking.getStart().isAfter(now))
					.min(Comparator.comparing(Booking::getStart)).orElse(null);

			Booking lastBooking = bookingListSorted.stream().filter(booking -> booking.getEnd().isBefore(now))
					.max(Comparator.comparing(Booking::getEnd)).orElse(null);

			itemDto.setNextBooking(nextBooking);
			itemDto.setLastBooking(lastBooking);

			List<Long> itemsIdList = bookingMap.keySet().stream().toList();
			Map<Long, List<Comment>> commemtsMap = getCommentsByItems(itemsIdList);
			List<Comment> commentsList = commemtsMap.get(itemDtoId) == null ? Collections.emptyList()
					: commemtsMap.get(itemDtoId);
			List<ResponseCommentDto> commentsDtoList = commentsList.stream()
					.map(CommentMapper::commentToResponseCommentDto).toList();
			itemDto.setComments(commentsDtoList);
		}
		return responseItemsListDto;
	}

	private Map<Long, List<Booking>> getBookingsByItems(List<Item> itemList) {
		List<Booking> bookingList = bookingRepository.findByItemIn(itemList);
		return bookingList.stream()
				.collect(Collectors.groupingBy(booking -> booking.getItem().getId(), Collectors.toList()));
	}

	private Map<Long, List<Comment>> getCommentsByItems(List<Long> itemsIdList) {
		List<Comment> commentsList = commentRepository.findByItemIn(itemsIdList);
		return commentsList.stream()
				.collect(Collectors.groupingBy(comment -> comment.getItem().getId(), Collectors.toList()));
	}
}