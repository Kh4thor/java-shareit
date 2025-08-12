package ru.practicum.shareit.item.mvc.controller.repository;

import java.util.List;
import java.util.Optional;

import ru.practicum.shareit.item.dto.FindItemDto;
import ru.practicum.shareit.item.mvc.model.Item;

public interface ItemRepositoryApp {

	Optional<Item> createItem(Item createItemDto);

	Optional<Item> updateItem(Item createItemDto);

	Optional<Item> getItem(Long itemId);

	Boolean isItemExists(Long itemId);

	Boolean setOwnerToItem(Item item);

	Boolean isOwnerExists(Long ownerId);

	Boolean deleteOwner(Long ownerId);

	Optional<Item> deleteItem(Long itemId);

	Boolean isItemBelongsOwner(Long itemId, Long ownerId);

	List<Item> getAllItems();

	List<Item> deleteAllItems();

	List<Item> getItemsOfOwner(Long ownerId);

	List<Item> searchItemByText(FindItemDto findItemDto);

	Boolean deleteAllOwners();

	List<Item> deleteItemsOfOwner(Long ownerId);

	Long deleteItemFromOwner(Long itemId);

	Boolean isItemHasOwner(Long itemId);
}