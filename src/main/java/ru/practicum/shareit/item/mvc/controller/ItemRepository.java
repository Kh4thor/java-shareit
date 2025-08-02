package ru.practicum.shareit.item.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ResponseItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.utills.ResponseItemDtoMapper;

@Component
public class ItemRepository {

	private final JdbcTemplate jdbcTemplate;
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public ItemRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public ResponseItemDto createItem(CreateItemDto itemDto, Long ownerId) {
		String createItemSql = ""
				+ "INSERT INTO items (name, description, available, owner_id) "
				+ "VALUES (:name, :description, :available, :owner_id)";

		Map<String, Object> createItemParams = new HashMap<>();
		createItemParams.put("name", itemDto.getName());
		createItemParams.put("description", itemDto.getDescription() != null ? itemDto.getDescription() : null);
		createItemParams.put("available", itemDto.getAvailable());
		createItemParams.put("owner_id", ownerId);

		namedParameterJdbcTemplate.update(createItemSql, createItemParams);
		
		return getItemByMaxId();
	}
	
	public ResponseItemDto updateItem(UpdateItemDto itemDto, Long itemId) {
		String updateItemNameSql = ""
				+ "UPDATE items "
				+ "SET name = :name, description = :description, available = :available "
				+ "WHERE id = :id";

		ResponseItemDto item = getItem(itemId);

		String nameCurrentValue = item.getName();
		String nameToUpdateValue = itemDto.getName();

		String descriptionCurrentValue = item.getDescription();
		String descriptionToUpdateValue = itemDto.getDescription();

		Boolean availableCurrentValue = item.getAvailable();
		Boolean availableToUpdateValue = itemDto.getAvailable();

		String name = nameToUpdateValue == null ? nameCurrentValue : nameToUpdateValue;
		String description = descriptionToUpdateValue == null ? descriptionCurrentValue : descriptionToUpdateValue;
		Boolean available = availableToUpdateValue == null ? availableCurrentValue : availableToUpdateValue;

		Map<String, Object> updateItemParams = new HashMap<>();
		updateItemParams.put("id", itemId);
		updateItemParams.put("name", name);
		updateItemParams.put("description", description);
		updateItemParams.put("available", available);

		namedParameterJdbcTemplate.update(updateItemNameSql, updateItemParams);

		return getItem(itemId);
	}

	public ResponseItemDto getItem(Long itemId) {
		String getItemSql = ""
				+ "SELECT * "
				+ "FROM items "
				+ "WHERE id = :id AND activity = :activity";

		Map<String, Object> getItemParams = new HashMap<>();
		getItemParams.put("id", itemId);
		getItemParams.put("activity", true);
		return namedParameterJdbcTemplate.queryForObject(getItemSql, getItemParams, new ResponseItemDtoMapper());
	}

	public Boolean isItemExists(Long itemId) {
		String isItemExistsSql = ""
				+ "SELECT EXISTS (SELECT 1 "
								+ "FROM items "
								+ "WHERE id = :id AND activity = :activity)";

		Map<String, Object> setOwnerToItemParams = new HashMap<>();
		setOwnerToItemParams.put("id", itemId);
		setOwnerToItemParams.put("activity", true);

		return namedParameterJdbcTemplate.queryForObject(isItemExistsSql, setOwnerToItemParams, Boolean.class);
	}

	public Boolean setOwnerToItem(Long itemId, Long ownerId) {
		String setOwnerToItemSql = ""
				+ "INSERT INTO items_owners (item_id, owner_id) "
				+ "VALUES (:item_id, :owner_id)";
		
		Map<String, Object> setOwnerToItemParams = new HashMap<>();
		setOwnerToItemParams.put("item_id", itemId);
		setOwnerToItemParams.put("owner_id", ownerId);
		
		return namedParameterJdbcTemplate.update(setOwnerToItemSql, setOwnerToItemParams) == 1;
	}
	
	public boolean isOwnerExists(Long ownerId) {
		String isOwnerExistsSql = ""
				+ "SELECT EXISTS (SELECT 1 "
								+ "FROM items_owners "
								+ "WHERE owner_id = :owner_id AND activity = :activity)";
		
		Map<String, Object> setOwnerToItemParams = new HashMap<>();
		setOwnerToItemParams.put("owner_id", ownerId);
		setOwnerToItemParams.put("activity", true);

		return namedParameterJdbcTemplate.queryForObject(isOwnerExistsSql, setOwnerToItemParams, Boolean.class);
	}

	public Boolean deleteOwner(Long ownerId) {
		String removeOwnerSql = ""
				+ "UPDATE items_owners"
				+ "SET activity = :activity "
				+ "WHERE owner_id = :owner_id";

		Map<String, Object> setOwnerToItemParams = new HashMap<>();
		setOwnerToItemParams.put("activity", false);
		setOwnerToItemParams.put("owner_id", ownerId);

		return namedParameterJdbcTemplate.update(removeOwnerSql, setOwnerToItemParams) > 0;
	}
	
	public ResponseItemDto deleteItem(Long itemId) {
		
		ResponseItemDto item = getItem(itemId);
		
		String deleteItemSql = ""
				+ "UPDATE items "
				+ "SET activity = :activity "
				+ "WHERE item_id = :id";
		
		Map<String, Object> deleteItemParams = new HashMap<>();
		deleteItemParams.put("activity", false);
		deleteItemParams.put("owner_id", itemId);
		
		namedParameterJdbcTemplate.update(deleteItemSql, deleteItemParams);
		
		return item;
	}
	
	public boolean isItemBelongsOwner(Long itemId, Long ownerId) {
		String isItemBelongsOwnerSql = ""
				+ "SELECT EXISTS (SELECT 1 "
								+ "FROM items_owners "
								+ "WHERE item_id = :item_id AND owner_id = :owner_id)";

		Map<String, Object> isItemBelongsOwnerParams = new HashMap<>();
		isItemBelongsOwnerParams.put("item_id", itemId);
		isItemBelongsOwnerParams.put("owner_id", itemId);

		return namedParameterJdbcTemplate.queryForObject(isItemBelongsOwnerSql, isItemBelongsOwnerParams, Boolean.class);
	}

	public List<ResponseItemDto> getAllItems() {
		String getAllItemsSql = ""
				+ "SELECT * "
				+ "FROM items "
				+ "WHERE activity = :activity";

		return jdbcTemplate.query(getAllItemsSql, new ResponseItemDtoMapper(), true);
	}
	
	public List<ResponseItemDto> deleteAllItems() {
		String deleteAllItemsSql = ""
				+ "UPDATE items "
				+ "SET activity = :newActivity "
				+ "WHERE activity = :currentActivity";
		
		Map<String, Object> deleteAllItemsParams = new HashMap<>();
		deleteAllItemsParams.put("newActivity", false);
		deleteAllItemsParams.put("currentActivity", true);

		List<ResponseItemDto> itemsListToDelete = getAllItems();

		namedParameterJdbcTemplate.update(deleteAllItemsSql, deleteAllItemsParams);

		return itemsListToDelete;
	}
	
	public List<ResponseItemDto> getItemsOfOwner (Long ownerId) {
		String getItemsOfOwnerSql = ""
				+ "SELECT * "
				+ "FROM items "
				+ "WHERE id IN (SELECT item_id "
							+ "FROM items_owners "
							+ "WHERE owner_id = ?)";
		return jdbcTemplate.query(getItemsOfOwnerSql, new ResponseItemDtoMapper(), ownerId);
	}

	private ResponseItemDto getItemByMaxId() {
		String lastAddedItemIdSql = "" + "SELECT * " + "FROM items " + "WHERE id = (SELECT MAX (id) " + "FROM items)";
		return jdbcTemplate.queryForObject(lastAddedItemIdSql, new ResponseItemDtoMapper());
	}

	public List<ResponseItemDto> searchItemByText(String text, Long ownerId) {
		String searchItemByTextSql = ""
		        + "SELECT i.* "
		        + "FROM items i "
		        + "JOIN items_owners io ON io.item_id = i.id "
				+ "WHERE (LOWER(i.name) LIKE LOWER(:text) OR LOWER(i.description) LIKE LOWER(:text)) "
		        + "AND i.owner_id = :owner_id "
				+ "AND i.activity = :activity "
		        + "AND available = :available;";

		    Map<String, Object> params = new HashMap<>();
		    params.put("owner_id", ownerId);
		    params.put("activity", true);
		    params.put("text", "%" + text + "%");
			params.put("available", true);

		    return namedParameterJdbcTemplate.query(searchItemByTextSql, params, new ResponseItemDtoMapper());
		}
}