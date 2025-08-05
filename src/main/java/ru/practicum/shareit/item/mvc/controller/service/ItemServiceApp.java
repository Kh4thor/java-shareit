package ru.practicum.shareit.item.mvc.controller.service;

import java.util.List;

import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.FindItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.mvc.model.Item;

public interface ItemServiceApp {

	Item createItem(CreateItemDto createItemDto);

	Item updateItem(UpdateItemDto updateItemDto);

	Item getItem(Long itemId);

	Item deleteItem(Long itemId);

	List<Item> deleteAllItems();

	List<Item> getItemsOfOwner(Long userId);

	List<Item> searchItemByText(FindItemDto findItemDto);

}