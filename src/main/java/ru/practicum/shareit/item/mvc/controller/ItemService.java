package ru.practicum.shareit.item.mvc.controller;

import java.util.List;

import org.springframework.stereotype.Service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mvc.model.Item;
import ru.practicum.shareit.user.mvc.controller.UserService;

@Service
public class ItemService {

	public ItemRepositry itemRepository;
	public UserService userService;

	public ItemService(ItemRepositry itemRepositry, UserService userService) {
		this.itemRepository = itemRepositry;
		this.userService = userService;
	}

	public Item createItem(ItemDto itemDto, Long userId) {
		itemDto.setOwnerId(userId);
		return itemRepository.createItem(itemDto);
	}

	public Item updateItem(ItemDto itemDto, Long userId) {
		itemDto.setOwnerId(userId);
		return itemRepository.updateItem(itemDto);
	}

	public List<Item> searchItemByText(String text) {
		return itemRepository.searchItemByText(text);
	}

}