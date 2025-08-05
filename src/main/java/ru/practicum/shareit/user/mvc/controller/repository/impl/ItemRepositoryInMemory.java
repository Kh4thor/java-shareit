package ru.practicum.shareit.user.mvc.controller.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ru.practicum.shareit.item.dto.FindItemDto;
import ru.practicum.shareit.item.mvc.controller.repository.ItemRepositoryApp;
import ru.practicum.shareit.item.mvc.model.Item;

@Repository
public class ItemRepositoryInMemory implements ItemRepositoryApp {
	Map<Long, Item> items = new HashMap<>();
	Map<Long, List<Long>> owner_items = new HashMap<>();

	private Long itemIdCounter = 0L;

	private Long generateItemId() {
		return ++itemIdCounter;
	}

	@Override
	public Item createItem(Item item) {
		Long itemId = generateItemId();
		items.put(itemId, item);
		return items.get(itemId);
	}

	@Override
	public Item updateItem(Item item) {
		Long itemId = item.getId();
		String updateItemName = item.getName();
		String updateDescription = item.getDescription();
		Boolean updateAvailable = item.getAvaliable();
 		
		Item updatedItem = items.get(itemId);
		String name = 
		String description = 
		String ...
		
		updatedItem = updatedItem.setId(itemId);

		return null;
	}

	@Override
	public Item getItem(Long itemId) {
		return items.get(itemId);
	}

	@Override
	public Boolean isItemExists(Long itemId) {
		return items.containsKey(itemId);
	}

	@Override
	public Boolean setOwnerToItem(Long itemId, Long ownerId) {
		return null;
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public Boolean isOwnerExists(Long ownerId) {
		return owner_items.containsKey(ownerId);
	}

	@Override
	public Boolean deleteOwner(Long ownerId) {
		owner_items.remove(ownerId);
		return !isOwnerExists(ownerId);
	}

	@Override
	public Item deleteItem(Long itemId) {
		Item itemToDelete = items.get(itemId);
		items.remove(itemId);
		return itemToDelete;
	}

	@Override
	public Boolean isItemBelongsOwner(Long itemId, Long ownerId) {
		if (owner_items.get(ownerId).contains(itemId)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Item> getAllItems() {
		return new ArrayList<>(items.values());
	}

	@Override
	public List<Item> deleteAllItems() {
		List<Item> deleteItemsList = new ArrayList<>(items.values());
		items.clear();
		return deleteItemsList;
	}

	@Override
	public List<Item> getItemsOfOwner(Long ownerId) {
		List<Long> itemsOfOwnerIdList = owner_items.get(ownerId);
		return	itemsOfOwnerIdList
				.stream()
				.map(itemId -> items.get(itemId))
				.toList();
	}

	@Override
	public List<Item> searchItemByText(FindItemDto findItemDto) {
		Long ownerId = findItemDto.getOwnerId();
		String text = findItemDto.getText();
		
		return	getItemsOfOwner(ownerId)
				.stream()
				.filter(e-> e.getName().equals(text) || e.getDescription().equals(text))
				.toList();
	}
}
