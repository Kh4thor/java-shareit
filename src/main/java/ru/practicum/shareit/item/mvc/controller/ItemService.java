package ru.practicum.shareit.item.mvc.controller;

import org.springframework.stereotype.Service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mvc.model.Item;

@Service
public class ItemService {

	public ItemRepositry itemRepositry;

	public ItemService(ItemRepositry itemRepositry) {
		this.itemRepositry = itemRepositry;
	}

	public Item createItem(ItemDto itemDto) {
		return itemRepositry.createItem(itemDto);
	}
}