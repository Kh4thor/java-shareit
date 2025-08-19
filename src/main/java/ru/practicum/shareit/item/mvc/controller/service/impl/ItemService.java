package ru.practicum.shareit.item.mvc.controller.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.booking.exception.BookingNotFoundException;
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
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.mvc.controller.repository.UserRepositoryApp;
import ru.practicum.shareit.user.mvc.model.User;

@Slf4j
@Service
public class ItemService implements ItemServiceApp {

	private final ItemRepositoryApp itemRepository;
	private final UserRepositoryApp userRepository;
	private final CommentRepositoryApp commentRepository;
	private final UserException userException;

	public ItemService(ItemRepositoryApp itemRepositry, UserException userException, UserRepositoryApp userRepository, CommentRepositoryApp commentRepository) {
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

		log.info("Начато преобразование (CreateItemDto)createItemDto в объект класса Item. Получен объект: " + createItemDto);
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

		log.info("Начато преобразование (UpdateItemDto)updateItemDto в объект класса User. Получен объект: " + updateItemDto);
		Item updateItem = updateItemDtoToItem(updateItemDto, errorMessage);
		log.info("updateUserDto преобразован в объект класса User: " + updateItem);

		log.info("Начато обновление предмета. Получен объект:" + updateItem);
		
		Long itemId = updateItemDto.getItemId();
		
		log.info("Начат вызов предмета. Получен id:" + itemId);
		Item itemFromDb = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId, errorMessage));
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
	public ResponseItemDto getItem(Long itemId) {
		String errorMessage = "Невозможно вызвать объект";

		log.info("Начат вызов предмета. Получен id:" + itemId);
		Item responseItem = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId, errorMessage));
		log.info("Вызван предмет:" + itemId);

		log.info("Начато преобразование ItemCreateDto в объект ResponseItemDt. Получен объект:" + responseItem);
		ResponseItemDto responseItemDto = ItemMapper.itemToResponseItemDto(responseItem);
		log.info("Закончено преобразование ItemCreateDto в объект ResponseItemDt. Получен объект:" + responseItemDto);

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
	public List<ResponseItemDto> getItemsOfOwner(Long userId) {
		String errorMessage = "Невозможно получить список предметов пользователя.";
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId, errorMessage));
		
		log.info("Начат процесс получения списка предметов пользователя. Получен id-пользователя=" + userId);
		List<Item> responseItemsList = user.getItems();
		log.info("Получен список предметов пользователя" + responseItemsList);

		log.info("Начато преобразование списка ItemCreateDto в список объектов ResponseItemDt. Получен объект:" + responseItemsList);
		List<ResponseItemDto> responseItemsListDto = 	responseItemsList
														.stream()
														.map(ItemMapper::itemToResponseItemDto)
														.toList();
		log.info("Закончено преобразование ItemCreateDto в объект ResponseItemDt. Получен объект:" + responseItemsListDto);

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
		List<Item> responseItemsList = itemRepository.searchItemByText(ownerId, text);

		log.info("Начато преобразование списка ItemCreateDto в список объектов ResponseItemDt. Получен объект:" + responseItemsList);
		List<ResponseItemDto> responseItemsListDto = 	responseItemsList
														.stream()
														.map(ItemMapper::itemToResponseItemDto)
														.toList();
		log.info("Закончено преобразование ItemCreateDto в объект ResponseItemDt. Получен объект:" + responseItemsListDto);
		return responseItemsListDto;
	}

	@Override
	public ResponseCommentDto createComment(CreateCommentDto createCommentDto) {
		String errorMessage = "Невозможно создать комментарий.";
		Comment comment = createCommentDtoToComment(createCommentDto, errorMessage);

		comment.setCreated(LocalDateTime.now());
		Comment createdComment = commentRepository.save(comment);
		
		Item item = getItem(createCommentDto.getItemId(), errorMessage);
		
		List<Booking> bookingList = item.getBookings();
		Booking booking =	bookingList.stream()
							.filter(b -> b.getItem().equals(item))
							.findAny()
							.orElseThrow(()-> new BookingNotFoundException(null, errorMessage));

		LocalDateTime timeOfCommentCreation = createdComment.getCreated();
		LocalDateTime timeOfBookingEnd = booking.getEnd();

		if (timeOfCommentCreation.isBefore(timeOfBookingEnd)) {
			throw new IllegalDateOfComment(timeOfCommentCreation, timeOfBookingEnd, errorMessage);
		}

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
}