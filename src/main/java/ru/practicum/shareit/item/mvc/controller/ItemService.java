package ru.practicum.shareit.item.mvc.controller;

import org.springframework.stereotype.Service;

import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ResponseItemDto;
import ru.practicum.shareit.user.exception.UserException;

@Service
public class ItemService {

	private final ItemRepositry itemRepository;
	private final UserException userException;
	private final ItemException itemException;

	public ItemService(ItemRepositry itemRepositry, UserException userException) {
		this.itemRepository = itemRepositry;
		this.userException = userException;
	}

	public ResponseItemDto createItem(CreateItemDto itemDto, Long ownerId) {
		String errorMessage = "Невозможно создать объект.";
		userException.checkUserNotFoundException(ownerId, errorMessage);
		return itemRepository.createItem(itemDto, ownerId);
	}
}