package ru.practicum.shareit.item.mvc.controller.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import ru.practicum.shareit.item.dto.FindItemDto;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.exception.OwnerOfItemNotFoundException;
import ru.practicum.shareit.item.mvc.controller.repository.ItemRepositoryApp;
import ru.practicum.shareit.item.mvc.model.Item;

@Repository
public class ItemRepositoryInMemory implements ItemRepositoryApp {
	Map<Long, Item> items = new HashMap<>();
	Map<Long, List<Long>> itemsOfOwnersMap = new HashMap<>();

	private Long itemIdCounter = 0L;

	private Long generateItemId() {
		return ++itemIdCounter;
	}

	@Override
	public Boolean setOwnerToItem(Item item) {
		Long itemId = item.getId();
		Long ownerId = item.getOwner().getId();

		List<Long> itemsIdList = new ArrayList<>();
		itemsIdList.add(itemId);
		itemsOfOwnersMap.put(ownerId, itemsIdList);
		List<Long> listOfItemsIdOfOwner = itemsOfOwnersMap.get(ownerId);
		listOfItemsIdOfOwner.add(itemId);

		return listOfItemsIdOfOwner.contains(item.getId());
	}

	@Override
	public Item createItem(Item createItem) {
		Long itemId = generateItemId();
		createItem.setId(itemId);
		items.put(itemId, createItem);
		return getItem(itemId);
	}

	@Override
	public Item updateItem(Item updateItem) {
		Long itemId = updateItem.getId();
		Item currentItem = getItem(itemId);

		String nameCurrentValue = currentItem.getName();
		String nameToUpdateValue = updateItem.getName();

		String descriptionCurrentValue = currentItem.getDescription();
		String descriptionToUpdateValue = updateItem.getDescription();

		Boolean availableCurrentValue = currentItem.getAvailable();
		Boolean availableToUpdateValue = updateItem.getAvailable();

		String name = (nameToUpdateValue == null || nameToUpdateValue.isBlank()) ? nameCurrentValue : nameToUpdateValue;

		String description = (descriptionToUpdateValue == null || descriptionToUpdateValue.isBlank()) ?
				descriptionCurrentValue : descriptionToUpdateValue;

		Boolean available = availableToUpdateValue == null ? availableCurrentValue : availableToUpdateValue;

		updateItem.setName(name);
		updateItem.setDescription(description);
		updateItem.setAvailable(available);

		items.put(itemId, updateItem);

		return getItem(itemId);
	}

	@Override
	public Item getItem(Long itemId) {
		Optional<Item> itemOtp = Optional.ofNullable(items.get(itemId));
		return itemOtp.orElseThrow(() -> new ItemNotFoundException(itemId));
	}

	@Override
	public Boolean isItemExists(Long itemId) {
		return items.containsKey(itemId);
	}

	@Override
	public Boolean isOwnerExists(Long ownerId) {
		return itemsOfOwnersMap.containsKey(ownerId);
	}

	@Override
	public Boolean deleteOwner(Long ownerId) {
		List<Long> itemsIdList = itemsOfOwnersMap.get(ownerId);
		for (int i = 0; i < itemsIdList.size(); i++) {
			Long itemId = itemsIdList.get(i);
			items.remove(itemId);
		}

		itemsOfOwnersMap.remove(ownerId);
		return !isOwnerExists(ownerId);
	}

	@Override
	public Boolean deleteAllOwners() {
		itemsOfOwnersMap.clear();
		return itemsOfOwnersMap.isEmpty();
	}

	@Override
	public Item deleteItem(Long itemId) {
		Item itemToDelete = items.get(itemId);
		items.remove(itemId);
		return itemToDelete;
	}

	private Long getOwnerIdByItemId(Long itemId) {
		for (Long ownerId : itemsOfOwnersMap.keySet()) {
			List<Long> itemsIdList = itemsOfOwnersMap.get(ownerId);
			if (itemsIdList.contains(itemId)) {
				return ownerId;
			}
		}
		throw new OwnerOfItemNotFoundException(itemId);
	}

	@Override
	public Long deleteItemFromOwner(Long itemId) {
		itemsOfOwnersMap.values().forEach(itemsList -> itemsList.removeIf(id -> id.equals(itemId)));
		itemsOfOwnersMap.entrySet().removeIf(entry -> entry.getValue().isEmpty());

		return getOwnerIdByItemId(itemId);
		}

	@Override
	public Boolean isItemBelongsOwner(Long itemId, Long ownerId) {
		if (itemsOfOwnersMap.get(ownerId) == null) {
			return false;
		}
		return itemsOfOwnersMap.get(ownerId).contains(itemId);
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
		List<Long> itemsOfOwnerIdList = itemsOfOwnersMap.get(ownerId);
		if (itemsOfOwnerIdList == null) {
			return new ArrayList<Item>();
		}
		return	itemsOfOwnerIdList
				.stream()
				.map(itemId -> items.get(itemId))
				.toList();
	}

	@Override
	public List<Item> searchItemByText(FindItemDto findItemDto) {
		Long ownerId = findItemDto.getOwnerId();
		String text = findItemDto.getText();

		return getItemsOfOwner(ownerId).stream()
				.filter(e -> (e.getName().toLowerCase().contains(text.toLowerCase())
						|| e.getDescription().toLowerCase().contains(text.toLowerCase()))
						&& e.getAvailable())
				.distinct()
				.toList();
	}

	@Override
	public List<Item> deleteItemsOfOwner(Long ownerId) {
		if (!isOwnerExists(ownerId)) {
			return new ArrayList<Item>();
		}

		List<Item> itemsOfOwnerList = getItemsOfOwner(ownerId);
		itemsOfOwnersMap.get(ownerId).clear();

		return itemsOfOwnerList;
	}

	@Override
	public Boolean isItemHasOwner(Long itemId) {
		 return itemsOfOwnersMap.values().stream()
					.flatMap(Collection::stream)
					.anyMatch(id -> id.equals(itemId));
	}
}