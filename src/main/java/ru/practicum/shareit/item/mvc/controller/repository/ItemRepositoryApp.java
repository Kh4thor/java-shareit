package ru.practicum.shareit.item.mvc.controller.repository;

import java.util.List;

import ru.practicum.shareit.item.dto.FindItemDto;
import ru.practicum.shareit.item.mvc.model.Item;

public interface ItemRepositoryApp {

	Item createItem(Item createItemDto);

	Item updateItem(Item createItemDto);

	Item getItem(Long itemId);

	Boolean isItemExists(Long itemId);

	Boolean setOwnerToItem(Item item);

	Boolean isOwnerExists(Long ownerId);

	Boolean deleteOwner(Long ownerId);

	Item deleteItem(Long itemId);

	Boolean isItemBelongsOwner(Long itemId, Long ownerId);

	List<Item> getAllItems();

	List<Item> deleteAllItems();

	List<Item> getItemsOfOwner(Long ownerId);

	List<Item> searchItemByText(FindItemDto findItemDto);

	Boolean deleteAllOwners();
}