package ru.practicum.shareit.item.mvc.controller.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.booking.exception.BookingNotFoundException;
import ru.practicum.shareit.booking.mvc.controller.repository.BookingRepositoryApp;
import ru.practicum.shareit.booking.mvc.model.Booking;
import ru.practicum.shareit.item.dto.CreateCommentDto;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.FindItemDto;
import ru.practicum.shareit.item.dto.ResponseCommentDto;
import ru.practicum.shareit.item.dto.ResponseItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.exception.IllegalDateOfComment;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.mvc.controller.repository.CommentRepositoryApp;
import ru.practicum.shareit.item.mvc.controller.repository.ItemRepositoryApp;
import ru.practicum.shareit.item.mvc.controller.service.ItemServiceApp;
import ru.practicum.shareit.item.mvc.model.Comment;
import ru.practicum.shareit.item.mvc.model.Item;
import ru.practicum.shareit.item.utills.CommentMapper;
import ru.practicum.shareit.item.utills.ItemMapper;
import ru.practicum.shareit.user.exception.UserException;
import ru.practicum.shareit.user.exception.UserNotBookerOfItemException;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.mvc.controller.repository.UserRepositoryApp;
import ru.practicum.shareit.user.mvc.model.User;

@Slf4j
@Service
public class ItemService implements ItemServiceApp {

	private final UserException userException;
	private final ItemRepositoryApp itemRepository;
	private final UserRepositoryApp userRepository;
	private final BookingRepositoryApp bookingRepository;
	private final CommentRepositoryApp commentRepository;

	public ItemService(UserException userException, ItemRepositoryApp itemRepositry, UserRepositoryApp userRepository,
			CommentRepositoryApp commentRepository, BookingRepositoryApp bookingRepository) {

		this.bookingRepository = bookingRepository;
		this.commentRepository = commentRepository;
		this.itemRepository = itemRepositry;
		this.userRepository = userRepository;
		this.userException = userException;
	}

	@Override
	public ResponseItemDto createItem(@NotNull CreateItemDto createItemDto) {
		Long ownerId = createItemDto.getOwnerId();

		String errorMessage = "Невозможно создать предмет.";
		userException.checkUserNotFoundException(ownerId, errorMessage);

		log.info("Начато преобразование (CreateItemDto)createItemDto в объект класса Item. Получен объект: "
				+ createItemDto);
		Item createItem = createItemDtoToItem(createItemDto, errorMessage);
		log.info("(CreateItemDto)createItemDto преобразован в объект класса User: " + createItem);

		log.info("Начато создание предмета. Получен объект:" + createItem);
		Item responseItem = itemRepository.save(createItem);
		log.info("Создан предмет: " + responseItem);

		log.info("Начато преобразование ItemCreateDto в объект ResponseItemDt. Получен объект:" + responseItem);
		ResponseItemDto responseItemDto = ItemMapper.itemToResponseItemDto(responseItem);
		log.info("Закончено преобразование ItemCreateDto в объект ResponseItemDt. Получен объект:" + responseItemDto);

		return responseItemDto;
	}

	@Override
	public ResponseItemDto updateItem(UpdateItemDto updateItemDto) {

		String errorMessage = "Невозможно обновить предмет";

		log.info("Начато преобразование (UpdateItemDto)updateItemDto в объект класса User. Получен объект: "
				+ updateItemDto);
		Item updateItem = updateItemDtoToItem(updateItemDto, errorMessage);
		log.info("updateUserDto преобразован в объект класса User: " + updateItem);

		log.info("Начато обновление предмета. Получен объект:" + updateItem);

		Long itemId = updateItemDto.getItemId();

		log.info("Начат вызов предмета. Получен id:" + itemId);
		Item itemFromDb = itemRepository.findById(itemId)
				.orElseThrow(() -> new ItemNotFoundException(itemId, errorMessage));
		log.info("Вызван предмет:" + itemId);

		String itemNameFromDb = itemFromDb.getName();
		String itemDescriptionFromDb = itemFromDb.getDescription();
		Boolean itemAvailableFromDb = itemFromDb.getAvailable();

		String itemNameToUpdate = updateItem.getName();
		String itemDescriptionToUpdate = updateItem.getDescription();
		Boolean itemAvailableToUpdate = updateItem.getAvailable();

		String name = itemNameToUpdate == null ? itemNameFromDb : itemNameToUpdate;
		String description = itemDescriptionToUpdate == null ? itemDescriptionFromDb : itemDescriptionToUpdate;
		Boolean available = itemAvailableToUpdate == null ? itemAvailableFromDb : itemAvailableToUpdate;

		updateItem.setName(name);
		updateItem.setDescription(description);
		updateItem.setAvailable(available);

		Item responseItem = itemRepository.save(updateItem);
		log.info("Обновлен предмет " + responseItem);

		log.info("Начато преобразование ItemCreateDto в объект ResponseItemDto. Получен объект:" + responseItem);
		ResponseItemDto responseItemDto = ItemMapper.itemToResponseItemDto(responseItem);
		log.info("Закончено преобразование ItemCreateDto в объект ResponseItemDt. Получен объект:" + responseItemDto);

		return responseItemDto;
	}

	@Override
	public ResponseItemDto getItemWithComments(Long itemId) {
		String errorMessage = "Невозможно вызвать объект";

		log.info("Начат вызов предмета. Получен id:" + itemId);
		Item item = getItem(itemId, errorMessage);
		log.info("Вызван предмет:" + itemId);

		ResponseItemDto responseItemDto = ItemMapper.itemToResponseItemDto(item);
		List<Comment> commentsList = findCommentsByItemId(itemId);
		commentsList = commentsList == null ? Collections.emptyList() : commentsList;

		log.info("Начато преобразование объектов Comment в объекты ResponseCommentDto. Получен объект:" + commentsList);
		List<ResponseCommentDto> responseCommentsList = commentsList.stream()
														.map(CommentMapper::commentToResponseCommentDto)
														.toList();
		log.info("Закончено преобразование объектов Comment в объекты ResponseCommentDto. Получен объект:" + responseCommentsList);

		responseItemDto.setComments(responseCommentsList);
		log.info("Закончено присваивание комментариев предмету: " + responseItemDto);

		return responseItemDto;
	}

	@Override
	public void deleteItem(Long itemId) {

		log.info("Начато удаление предмета. Получен id=" + itemId);
		itemRepository.deleteById(itemId);
		log.info("Удален предмет id=" + itemId);
	}

	@Override
	public void deleteAllItems() {
		log.info("Начато удаление всех предметов.");
		itemRepository.deleteAll();
		log.info("Все предметы удалены.");
	}

	@Override
	public List<ResponseItemDto> getItemsOfOwner(Long ownerId) {

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

			List<Booking> bookingList = bookingMap.get(itemDtoId) == null ? Collections.emptyList() : bookingMap.get(itemDtoId);
			List<Booking> bookingListSorted =	bookingList.stream()
												.sorted(Comparator.comparing(Booking::getStart))
												.toList();

			Booking	nextBooking = bookingListSorted.stream()
					.filter(booking -> booking.getStart().isAfter(now))
					.min(Comparator.comparing(Booking::getStart))
					.orElse(null);

			Booking	lastBooking = bookingListSorted.stream()
					.filter(booking -> booking.getEnd().isBefore(now))
					.max(Comparator.comparing(Booking::getEnd))
					.orElse(null);

			itemDto.setNextBooking(nextBooking);
			itemDto.setLastBooking(lastBooking);

			List<Long> itemsIdList = bookingMap.keySet().stream().toList();
			Map<Long, List<Comment>> commemtsMap = getCommentsByItems(itemsIdList);
			List<Comment> commentsList = commemtsMap.get(itemDtoId) == null ? Collections.emptyList() : commemtsMap.get(itemDtoId);
			List<ResponseCommentDto> commentsDtoList =	commentsList.stream()
														.map(CommentMapper::commentToResponseCommentDto)
														.toList();
			itemDto.setComments(commentsDtoList);
		}
		return responseItemsListDto;
	}

	@Override
	public List<ResponseItemDto> searchItemByText(FindItemDto findItemDto) {
		Long ownerId = findItemDto.getOwnerId();
		String text = findItemDto.getText();

		String errorMessage = "Невозможно начать поиск предмета владельца по строке поиска";
		userException.checkUserNotFoundException(ownerId, errorMessage);

		if (findItemDto.getText().isBlank()) {
			return new ArrayList<ResponseItemDto>();
		}

		log.info("Начат поиск предмета. Получен id-владельца: " + ownerId + " и строка поиска:" + text);
		List<Item> responseItemsList = itemRepository.findByOwnerIdAndText(ownerId, text);

		log.info("Начато преобразование списка ItemCreateDto в список объектов ResponseItemDt. Получен объект:"
				+ responseItemsList);
		List<ResponseItemDto> responseItemsListDto = responseItemsList.stream().map(ItemMapper::itemToResponseItemDto)
				.toList();
		log.info("Закончено преобразование ItemCreateDto в объект ResponseItemDt. Получен объект:"
				+ responseItemsListDto);
		return responseItemsListDto;
	}

	@Override
	public ResponseCommentDto createComment(CreateCommentDto createCommentDto) {
		String errorMessage = "Невозможно создать комментарий.";
		Comment comment = createCommentDtoToComment(createCommentDto, errorMessage);

		Long commentatorId = createCommentDto.getCommentatorId();
		List<Booking> bookingList = bookingRepository.findByUserIdAndState(commentatorId, "ALL");
		Long itemId = createCommentDto.getItemId();
		Item item = getItem(itemId, errorMessage);

		Booking booking =	bookingList.stream()
							.filter(b -> b.getItem().equals(item))
							.findAny()
							.orElseThrow(() -> new BookingNotFoundException(null, errorMessage));

		User booker = booking.getBooker();
		Long bookerId = booker.getId();
		if (!bookerId.equals(commentatorId)) {
			throw new UserNotBookerOfItemException(commentatorId, item.getId(), errorMessage);
		}

		comment.setCreated(LocalDateTime.now());
		LocalDateTime timeOfCommentCreation = comment.getCreated();
		LocalDateTime timeOfBookingEnd = booking.getEnd();
		if (timeOfCommentCreation.isAfter(LocalDateTime.now())) {
			throw new IllegalArgumentException("Время создания комментария не может быть в будущим. Получено: " + timeOfCommentCreation);
		}
		if (timeOfCommentCreation.isBefore(timeOfBookingEnd)) {
			throw new IllegalDateOfComment(timeOfCommentCreation, timeOfBookingEnd, errorMessage);
		}
		Comment createdComment = commentRepository.save(comment);
		return CommentMapper.commentToResponseCommentDto(createdComment);
	}

	private Comment createCommentDtoToComment(CreateCommentDto createCommentDto, String errorMessage) {
		Long commenatorId = createCommentDto.getCommentatorId();
		User commentator = getUser(commenatorId, errorMessage);
		Long itemId = createCommentDto.getItemId();
		Item item = getItem(itemId, errorMessage);

		Comment comment = CommentMapper.createCommentDtoToComment(createCommentDto);

		comment.setCommentator(commentator);
		comment.setItem(item);

		return comment;
	}

	private Map<Long, List<Comment>> getCommentsByItems(List<Long> itemsIdList) {
		List<Comment> commentsList = commentRepository.findByItemIn(itemsIdList);
		return commentsList.stream()
		        .collect(Collectors.groupingBy(
		            comment -> comment.getItem().getId(),
		            Collectors.toList()
		        ));
	}


	private Map<Long, List<Booking>> getBookingsByItems(List<Item> itemList) {
		List<Booking> bookingList = bookingRepository.findByItemIn(itemList);
	    return bookingList.stream()
	            .collect(Collectors.groupingBy(
	                booking -> booking.getItem().getId(),
	                Collectors.toList()
	            ));
	}

	private Item createItemDtoToItem(CreateItemDto createItemDto, String errorMessage) {
		Long ownerId = createItemDto.getOwnerId();
		User owner = userRepository.findById(ownerId).orElseThrow(() -> new UserNotFoundException(ownerId, errorMessage));
		Item item = ItemMapper.createItemDtoToItem(createItemDto);
		item.setOwner(owner);
		return item;
	}

	private Item updateItemDtoToItem(UpdateItemDto updateItemDto, String errorMessage) {
		Long ownerId = updateItemDto.getOwnerId();
		User owner = userRepository.findById(ownerId).orElseThrow(() -> new UserNotFoundException(ownerId, errorMessage));
		Item item = ItemMapper.updateItemDtoToItem(updateItemDto);
		item.setOwner(owner);
		return item;
	}

	private Item getItem(Long itemId, String errorMessage) {
		return itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId, errorMessage));
	}

	private User getUser(Long userId, String errorMessage) {
		return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId, errorMessage));
	}

	private List<Comment> findCommentsByItemId(Long itemId) {
		List<Comment> commentsList = commentRepository.findCommentsByItemId(itemId);
		commentsList = commentsList == null ? Collections.emptyList() : commentsList;
		return commentsList;
	}

}
