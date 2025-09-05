package ru.practicum.shareit.request;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.booking.mvc.controller.repository.BookingRepositoryApp;
import ru.practicum.shareit.item.dto.FindItemDto;
import ru.practicum.shareit.item.dto.ResponseItemDto;
import ru.practicum.shareit.item.mvc.controller.repository.CommentRepositoryApp;
import ru.practicum.shareit.item.mvc.controller.repository.ItemRepositoryApp;
import ru.practicum.shareit.item.mvc.model.Item;
import ru.practicum.shareit.item.utills.ItemMapper;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ResponseItemRequestDto;
import ru.practicum.shareit.request.exception.ItemRequestNotFoundException;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.utills.ItemRequestMapper;
import ru.practicum.shareit.user.exception.UserException;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.mvc.controller.repository.UserRepositoryApp;
import ru.practicum.shareit.user.mvc.model.User;

@Slf4j
@Service
public class ItemRequestService {

	private final UserException userException;

	private final UserRepositoryApp userRepository;
	private final ItemRepositoryApp itemRepository;
	private final ItemRequestRepositoryApp itemRequestRepository;

	public ItemRequestService(
			UserException userException,
			ItemRequestRepositoryApp itemRequestRepository,
			UserRepositoryApp userRepository,
			ItemRepositoryApp itemRepository,
			BookingRepositoryApp bookingRepository,
			CommentRepositoryApp commentRepository) {
		this.userException = userException;
		this.itemRepository = itemRepository;
		this.userRepository = userRepository;
		this.itemRequestRepository = itemRequestRepository;
	}

	public ResponseItemRequestDto createItemRequest(CreateItemRequestDto createRequestDto) {
		String errorMessge = "Невозможно создать запрос на бронироввание";
		ItemRequest itemRequestToCreate = enrichAndMappingCreateItemRequestDto(createRequestDto, errorMessge);
		ItemRequest createdItemRequest = itemRequestRepository.save(itemRequestToCreate);
		ResponseItemRequestDto responseItemRequestDto = ItemRequestMapper.itemRequestToResponseItemRequestDto(createdItemRequest);
		
		String text = createRequestDto.getDescription();
		Long ownerId = createRequestDto.getOwnerId();
		
		FindItemDto finditemDto =	FindItemDto.builder()
									.ownerId(ownerId)
									.text(text)
									.build();
		List<ResponseItemDto> responseItemDtoList = searchItemsByText(finditemDto);
		responseItemRequestDto.setItemsList(responseItemDtoList);
		
		return responseItemRequestDto;
	}

	public ResponseItemRequestDto getItemRequest(CreateItemRequestDto createRequestDto) {
		String errorMessage = "Невозможно получить запрос на бронирование";
		Long itemRequestId = createRequestDto.getRequestId();
		ItemRequest itemRequest = getItemRequest(itemRequestId, errorMessage);
		ResponseItemRequestDto responseItemRequestDto = ItemRequestMapper.itemRequestToResponseItemRequestDto(itemRequest);
		return responseItemRequestDto;
	}

	public List<ResponseItemRequestDto> getAllRequests() {
		return	itemRequestRepository.findAll().stream()
				.map(ItemRequestMapper::itemRequestToResponseItemRequestDto)
				.toList();
	}

	private ItemRequest getItemRequest(Long itemRequestId, String errorMessage) {
		return itemRequestRepository.findById(itemRequestId).orElseThrow(()->new ItemRequestNotFoundException(itemRequestId, errorMessage));
	}

	private ItemRequest enrichAndMappingCreateItemRequestDto(CreateItemRequestDto createItemRequestDto, String errorMessage) {
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

//	private List<ResponseItemDto> getItemsOfOwner(Long ownerId) {
//		log.info("Начат процесс получения списка предметов владельца. Получен id-владельца=" + ownerId);
//		List<Item> responseItemsList = itemRepository.findByOwnerId(ownerId);
//		log.info("Получен список предметов владельца" + responseItemsList);
//
//		log.info("Начато преобразование списка ItemCreateDto в список объектов ResponseItemDt. Получен объект:"	+ responseItemsList);
//		List<ResponseItemDto> responseItemsListDto =	responseItemsList.stream()
//														.map(ItemMapper::itemToResponseItemDto)
//														.toList();
//
//		log.info("Закончено преобразование ItemCreateDto в объект ResponseItemDt. Получен объект:"
//				+ responseItemsListDto);
//
//		Map<Long, List<Booking>> bookingMap = getBookingsByItems(responseItemsList);
//		LocalDateTime now = LocalDateTime.now();
//
//		for (int i = 0; i < responseItemsListDto.size(); i++) {
//			ResponseItemDto itemDto = responseItemsListDto.get(i);
//			Long itemDtoId = itemDto.getId();
//
//			List<Booking> bookingList = bookingMap.get(itemDtoId) == null ? Collections.emptyList()
//					: bookingMap.get(itemDtoId);
//
//			List<Booking> bookingListSorted =	bookingList.stream()
//												.sorted(Comparator.comparing(Booking::getStart))
//												.toList();
//
//			Booking nextBooking =	bookingListSorted.stream()
//									.filter(booking -> booking.getStart().isAfter(now))
//									.min(Comparator.comparing(Booking::getStart)).orElse(null);
//
//			Booking lastBooking =	bookingListSorted.stream()
//									.filter(booking -> booking.getEnd().isBefore(now))
//									.max(Comparator.comparing(Booking::getEnd)).orElse(null);
//
//			itemDto.setNextBooking(nextBooking);
//			itemDto.setLastBooking(lastBooking);
//
//			List<Long> itemsIdList = bookingMap.keySet().stream().toList();
//			Map<Long, List<Comment>> commemtsMap = getCommentsByItems(itemsIdList);
//			List<Comment> commentsList = commemtsMap.get(itemDtoId) == null ? Collections.emptyList()
//					: commemtsMap.get(itemDtoId);
//			List<ResponseCommentDto> commentsDtoList =	commentsList.stream()
//														.map(CommentMapper::commentToResponseCommentDto)
//														.toList();
//			itemDto.setComments(commentsDtoList);
//		}
//		return responseItemsListDto;
//	}

//	private Map<Long, List<Booking>> getBookingsByItems(List<Item> itemList) {
//		List<Booking> bookingList = bookingRepository.findByItemIn(itemList);
//	    return bookingList.stream()
//	            .collect(Collectors.groupingBy(
//	                booking -> booking.getItem().getId(),
//	                Collectors.toList()
//	            ));
//	}
//
//	private Map<Long, List<Comment>> getCommentsByItems(List<Long> itemsIdList) {
//		List<Comment> commentsList = commentRepository.findByItemIn(itemsIdList);
//		return commentsList.stream()
//		        .collect(Collectors.groupingBy(
//		            comment -> comment.getItem().getId(),
//		            Collectors.toList()
//		        ));
//	}

	private List<ResponseItemDto> searchItemsByText(FindItemDto findItemDto) {
		Long ownerId = findItemDto.getOwnerId();
		String text = findItemDto.getText();

		String errorMessage = "Невозможно начать поиск предмета владельца по строке поиска";
		userException.checkUserNotFoundException(ownerId, errorMessage);

		if (findItemDto.getText().isBlank()) {
			return new ArrayList<ResponseItemDto>();
		}
		log.info("Начат поиск предмета. Получен id-владельца: " + ownerId + " и строка поиска:" + text);
		List<Item> responseItemsList = itemRepository.findByOwnerIdAndText(ownerId, text);

		log.info("Начато преобразование списка ItemCreateDto в список объектов ResponseItemDt. Получен объект:"	+ responseItemsList);
		List<ResponseItemDto> responseItemsListDto =	responseItemsList.stream()
														.map(ItemMapper::itemToResponseItemDto)
														.toList();
		log.info("Закончено преобразование ItemCreateDto в объект ResponseItemDt. Получен объект:" + responseItemsListDto);
		return responseItemsListDto;
	}
}