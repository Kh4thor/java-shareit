package ru.practicum.shareit.item.mvc.controller;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ResponseItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.exception.ItemException;
import ru.practicum.shareit.user.exception.UserException;
import ru.practicum.shareit.user.mvc.controller.UserRepository;

@Slf4j
@Service
public class ItemService {

	private final ItemRepository itemRepository;
	private final UserException userException;
	private final ItemException itemException;

	public ItemService(ItemRepository itemRepositry, UserException userException, ItemException itemException,
			UserRepository userRepository) {
		this.itemException = itemException;
		this.userException = userException;
		this.itemRepository = itemRepositry;
	}

	public ResponseItemDto createItem(@NotNull CreateItemDto itemDto, Long ownerId) {
		String errorMessage = "Невозможно создать предмет.";
		userException.checkUserNotFoundException(ownerId, errorMessage);

		log.info("Начато создание предмета. Получен объект:" + itemDto + " и id-владельца=" + ownerId);
		ResponseItemDto createdItem = itemRepository.createItem(itemDto, ownerId);
		log.info("Создан предмет " + createdItem);

		Long itemId = createdItem.getId();
		setOwnerToItem(itemId, ownerId);

		return createdItem;
	}
	
	public ResponseItemDto updateItem(UpdateItemDto itemDto, Long ownerId, Long itemId) {
		log.info("Начато обновление предмета. Получен объект:" + itemDto + ", ownerId:" + ownerId +", itemId:" + itemId);
		ResponseItemDto updatedItem = itemRepository.updateItem(itemDto, itemId);
		log.info("Обновлен предмет " + updatedItem);
		return updatedItem;
	}

	public ResponseItemDto getItem(Long itemId) {
		return itemRepository.getItem(itemId);
	}

	public ResponseItemDto deleteItem(@NotNull Long itemId) {
		String errorMessage = "Невозможно удалить объект";
		itemException.checkItemNotFoundException(itemId, errorMessage);
		
		log.info("Начато удаление предмета. Получен id=" + itemId);
		ResponseItemDto deletedItem = itemRepository.deleteItem(itemId);
		log.info("Удален предмет:" + deletedItem);
		return deletedItem;
	}

	public List<ResponseItemDto> deleteAllItems() {
		log.info("Начато удаление всех предметов.");
		List<ResponseItemDto> deletedItemsList = itemRepository.deleteAllItems();
		log.info("Все предметы удалены.");
		return deletedItemsList;
	}

	private void setOwnerToItem(@NotNull Long itemId, @NotNull Long ownerId) {
		log.info("Начато добавление предмета id=" + itemId + " пользователю id=" + ownerId);
		if (itemRepository.setOwnerToItem(itemId, ownerId)) {
			log.info("Предмет id=" + itemId + " добавлен пользователю id=" + ownerId);
		}
	}
}