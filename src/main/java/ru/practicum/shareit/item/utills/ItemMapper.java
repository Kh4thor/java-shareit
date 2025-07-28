package ru.practicum.shareit.item.utills;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mvc.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.mvc.model.User;

@Slf4j
public class ItemMapper implements RowMapper<Item> {

	public static ItemDto toItemDto(Item item) {
		return	ItemDto.builder()
				.name(item.getName())
				.description(item.getDescription())
				.itemStatus(item.getItemStatus())
				.itemRequestId(item.getItemRequest() != null ? item.getItemRequest().getId() : null).build();
		}
	
		private User buildOwner(ResultSet rs) throws SQLException {
			Long ownerId = rs.getLong("owner_id");
			String ownerName = rs.getString("owner_name");
			String ownerEmail = rs.getString("owner_email");

			return	User.builder()
					.id(ownerId)
					.name(ownerName)
					.email(ownerEmail)
					.build();
	}

	private User buildRequestor(ResultSet rs) throws SQLException {
		Long requestorId = rs.getLong("requestor_id");
		String requestorName = rs.getString("requestor_name");
		String requestorEmail = rs.getString("requestor_email");

		return	User.builder()
				.id(requestorId)
				.name(requestorName)
				.email(requestorEmail)
				.build();
	}

	private ItemRequest buildItemRequest(ResultSet rs) throws SQLException {
		Long itemRequestId = rs.getLong("itemRequest_id");
		String description = rs.getString("item_description");
		User requestor = buildRequestor(rs);
		LocalDateTime created = LocalDateTime.parse(rs.getString("item_created"));
		
		return 	ItemRequest.builder()
				.id(itemRequestId)
				.description(description)
				.requestor(requestor)
				.created(created)
				.build();
	}
	
	@Override
	public Item mapRow(ResultSet rs, int rowNum) throws SQLException, IllegalArgumentException {
		
		try {
			ItemStatus itemStatus = ItemStatusConverter.convertStringToItemStatus(rs.getString("item_status"));
			User owner = buildOwner(rs);
			ItemRequest itemRequest = buildItemRequest (rs);

			return	Item.builder()
					.id(rs.getLong("item_id"))
					.name(rs.getString("item_name"))
					.description(rs.getString("item_description"))
					.itemStatus(itemStatus)
					.owner(owner)
					.itemRequest(itemRequest)
					.build();
		
		} catch (Exception e) {
			log.warn(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
