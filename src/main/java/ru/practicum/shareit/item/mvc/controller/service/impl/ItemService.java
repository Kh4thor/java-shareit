package ru.practicum.shareit.item.mvc.controller.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.FindItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.exception.ItemException;
import ru.practicum.shareit.item.mvc.controller.repository.ItemRepositoryApp;
import ru.practicum.shareit.item.mvc.controller.service.ItemServiceApp;
import ru.practicum.shareit.item.mvc.model.Item;
import ru.practicum.shareit.item.utills.ItemMapper;
import ru.practicum.shareit.user.exception.UserException;

@Slf4j
@Service
public class ItemService implements ItemServiceApp {

	private final ItemRepositoryApp itemRepository;
	private final UserException userException;
	private final ItemException itemException;
	private final ItemMapper itemMaper;

	public ItemService(ItemRepositoryApp itemRepositry, UserException userException, ItemException itemException, ItemMapper itemMaper) {
		this.itemException = itemException;
		this.userException = userException;
		this.itemRepository = itemRepositry;
		this.itemMaper = itemMaper;
	}

	@Override
	public Item createItem(@NotNull CreateItemDto createItemDto) {
		Long ownerId = createItemDto.getOwnerId();

		String errorMessage = "Невозможно создать предмет.";
		userException.checkUserNotFoundException(ownerId, errorMessage);
		
		log.info("Начато преобразование (CreateItemDto)createItemDto в объект класса Item. Получен объект: " + createItemDto);
		Item createItem = itemMaper.createItemDtoToItem(createItemDto);
		log.info("(CreateItemDto)createItemDto преобразован в объект класса User: " + createItem);

		log.info("Начато создание предмета. Получен объект:" + createItem);
		Item createdItem = itemRepository.createItem(createItem);
		log.info("Создан предмет: " + createdItem);

		Long itemId = createdItem.getId();
		setOwnerToItem(itemId, ownerId);

		return createdItem;
	}

	@Override
	public Item updateItem(UpdateItemDto updateItemDto) {
		Long ownerId = updateItemDto.getOwnerId();
		Long itemId = updateItemDto.getItemId();

		String errorMessage = "Невозможно обновить предмет";
		itemException.checkItemNotFoundException(itemId, errorMessage);
		userException.checkUserNotFoundException(ownerId, errorMessage);

		log.info("Начато преобразование (UpdateItemDto)updateItemDto в объект класса User. Получен объект: " + updateItemDto);
		Item updateItem = itemMaper.updateItemDtoToItem(updateItemDto);
		log.info("updateUserDto преобразован в объект класса User: " + updateItem);

		log.info("Начато обновление предмета. Получен объект:" + updateItem);
		Item responseItem = itemRepository.updateItem(updateItem);
		log.info("Обновлен предмет " + responseItem);
		return responseItem;
	}

	@Override
	public Item getItem(Long itemId) {
		String errorMessage = "Невозможно вызвать объект";
		itemException.checkItemNotFoundException(itemId, errorMessage);

		log.info("Начат вызов предмета. Получен id:" + itemId);
		Item gotItem = itemRepository.getItem(itemId);
		log.info("Вызван предмет:" + itemId);
		return gotItem;
	}

	@Override
	public Item deleteItem(Long itemId) {
		String errorMessage = "Невозможно удалить объект";
		itemException.checkItemNotFoundException(itemId, errorMessage);

		log.info("Начато удаление предмета. Получен id=" + itemId);
		Item deletedItem = itemRepository.deleteItem(itemId);
		log.info("Удален предмет:" + deletedItem);

		return deletedItem;
	}

	@Override
	public List<Item> deleteAllItems() {
		log.info("Начато удаление всех предметов.");
		List<Item> deletedItemsList = itemRepository.deleteAllItems();
		log.info("Все предметы удалены.");
		return deletedItemsList;
	}

	private void setOwnerToItem(@NotNull Long itemId, @NotNull Long ownerId) {
		String errorMessage = "Невозможно добавить предмет пользователю";
		itemException.checkItemAlreadyBelongsToTheOwnerException(itemId, ownerId, errorMessage);

		log.info("Начато добавление предмета id=" + itemId + " пользователю id=" + ownerId);
		if (itemRepository.setOwnerToItem(itemId, ownerId)) {
			log.info("Предмет id=" + itemId + " добавлен пользователю id=" + ownerId);
		}
	}

	@Override
	public List<Item> getItemsOfOwner(Long userId) {
		String errorMessage = "Невозможно получить список предметов пользователя";
		userException.checkUserNotFoundException(userId, errorMessage);

		log.info("Начат процесс получения списка предметов пользователя. Получен id-пользователя=" + userId);
		List<Item> itemsOfUserList = itemRepository.getItemsOfOwner(userId);
		log.info("Получен список предметов пользователя" + itemsOfUserList);
		return itemsOfUserList;
	}

	@Override
	public List<Item> searchItemByText(FindItemDto findItemDto) {
		Long ownerId = findItemDto.getOwnerId();
		String text = findItemDto.getText();

		String errorMessage = "Невозможно начать поиск предмета владельца по строке поиска";
		userException.checkUserNotFoundException(ownerId, errorMessage);

		if (findItemDto.getText().isBlank()) {
			return new ArrayList<Item>();
		}

		log.info("Начат поиск предмета. Получен id-владельца: " + ownerId + " и строка поиска:" + text);
		return itemRepository.searchItemByText(findItemDto);
	}
}