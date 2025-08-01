package ru.practicum.shareit.item.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ResponseItemDto;
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
	
	private ResponseItemDto getItemByMaxId() {
		String lastAddedItemIdSql = ""
				+ "SELECT *"
				+ "FROM items"
				+ "WHERE id = (SELECT MAX (id) "
							+ "FROM items)";
		return jdbcTemplate.queryForObject(lastAddedItemIdSql, new ResponseItemDtoMapper());
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
								+ "WHERE id = :id AND activity = :activity";

		Map<String, Object> setOwnerToItemParams = new HashMap<>();
		setOwnerToItemParams.put("id", itemId);
		setOwnerToItemParams.put("activity", true);

		return namedParameterJdbcTemplate.queryForObject(isItemExistsSql, setOwnerToItemParams, Boolean.class);
	}

	public void setOwnerToItem(Long itemId, Long ownerId) {
		String setOwnerToItemSql = ""
				+ "INSERT INTO items_owners (item_id, owner_id) "
				+ "VALUES (:item_id, :owner_id)";
		
		Map<String, Object> setOwnerToItemParams = new HashMap<>();
		setOwnerToItemParams.put("item_id", itemId);
		setOwnerToItemParams.put("owner_id", ownerId);
		
		namedParameterJdbcTemplate.update(setOwnerToItemSql, setOwnerToItemParams);
	}
	
	public Boolean isOwnerExists(Long ownerId) {
		String isOwnerExistsSql = ""
				+ "SELECT EXISTS (SELECT id"
								+ "FROM items_owners "
								+ "WHERE id = ? AND activity = :activity"
								+ "GROUP BY id";
		
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

		namedParameterJdbcTemplate.update(removeOwnerSql, setOwnerToItemParams);
		return !isOwnerExists(ownerId);
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

//	public 

//	public Item updateItem(CreateItemDto itemDto) {
//		String createItemSql = "" + "MERGE INTO items (name, description, status, owner_id, request_id) "
//				+ "VALUES (:name, :description, :status, :request_id) " + "WHERE id = :id";
//
//		Map<String, Object> createItemParams = new HashMap<>();
//		createItemParams.put("id", itemDto.getId());
//		createItemParams.put("name", itemDto.getName());
//		createItemParams.put("description", itemDto.getDescription());
//		createItemParams.put("avaliable", itemDto.getAvailable());
//		createItemParams.put("request_id", itemDto.getItemRequestId());
//
//		namedParameterJdbcTemplate.update(createItemSql, createItemParams);
//
//		return getItem(itemDto.getId());
//	}

	public List<ResponseItemDto> searchItemByText(String text) {
		String searchItemByTextSql = ""
				+ "SELECT * "
				+ "FROM items "
				+ "WHERE name LIKE :text OR description LIKE :text";
		Map<String, Object> searchItemByTextParams = new HashMap<>();
		searchItemByTextParams.put("text", "%" + text + "%");
		return namedParameterJdbcTemplate.query(searchItemByTextSql, searchItemByTextParams,
				new ResponseItemDtoMapper());
	}
}
