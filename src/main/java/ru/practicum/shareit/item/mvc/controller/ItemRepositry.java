package ru.practicum.shareit.item.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mvc.model.Item;
import ru.practicum.shareit.item.utills.ItemMapper;

public class ItemRepositry {

	private final JdbcTemplate jdbcTemplate;
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public ItemRepositry(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}


	public Item createItem(ItemDto itemDto) {

		String createItemSql = ""
				+ "INSERT INTO items (name, description, status, owner_id, request_id) "
				+ "VALUES (:name, :description, :status, :request_id)";

		Map<String, Object> createItemParams = new HashMap<>();
		createItemParams.put("name", itemDto.getName());

		createItemParams.put("description", itemDto.getDescription());
		createItemParams.put("status", itemDto.getItemStatus());
		createItemParams.put("request_id", itemDto.getItemRequestId());

		namedParameterJdbcTemplate.update(createItemSql, createItemParams);

		String lastAddedItemIdSql = ""
				+ "SELECT MAX (id) "
				+ "FROM users";

		Long lastAddedItemId = jdbcTemplate.queryForObject(lastAddedItemIdSql, Long.class);
		return getItem(lastAddedItemId);
	}
	
	public Item getItem (Long itemId) {
		String getItemSql = ""
				+ "SELECT * "
				+ "FROM items "
				+ "WHERE id = :id";
		Map<String, Object> getItemParams = new HashMap<>();
		getItemParams.put("id", itemId);
		return namedParameterJdbcTemplate.queryForObject(getItemSql, getItemParams, new ItemMapper());
	}
}
