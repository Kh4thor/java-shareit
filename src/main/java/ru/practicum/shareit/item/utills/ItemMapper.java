package ru.practicum.shareit.item.utills;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mvc.model.Item;

@Slf4j
public class ItemMapper implements RowMapper<Item> {

	public static ItemDto toItemDto(Item item) {
		return ItemDto.builder()
				.name(item.getName())
				.description(item.getDescription())
				.available(item.getAvaliable())
				.itemRequestId(item.getItemRequest() != null ? item.getItemRequest().getId() : null)
				.build();
		}
	
	@Override
	public Item mapRow(ResultSet rs, int rowNum) throws SQLException, IllegalArgumentException {
		// to-do
		return null;
	}
}
