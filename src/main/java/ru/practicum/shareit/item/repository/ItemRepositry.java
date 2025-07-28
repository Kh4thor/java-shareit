//package ru.practicum.shareit.item.repository;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//
//import ru.practicum.shareit.item.dto.ItemDto;
//import ru.practicum.shareit.item.model.Item;
//
//public class ItemRepositry {
//
//	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//
//	public ItemRepositry(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
//		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
//	}
//
//	public Item createItem(ItemDto itemDto) {
//
////		private String name;
////		private String description;
////		private ItemStatus itemStatus;
////		private Owner owner;
////		private ItemRequest request;
//
//		String createItemSql = ""
//				+ "INSERT INTO items (name, email) "
//				+ "VALUES ";
//
//		Map<String, Object> params = new HashMap<>();
//		params.put("name", itemDto.getName());
//		params.put("description", itemDto.getDescription());
//		params.put("itemStatus", itemDto.getItemStatus());
//		params.put("owner_id", itemDto.getOwner().getId());
//		params.put("request_id", itemDto.getRequest().getId());
//
//		namedParameterJdbcTemplate.update(createItemSql, params);
//	}
//
//	public Item getItem(Long itemId) {
//		String getItemSql = ""
//				+ "SELECT "
//					+ "id, "
//					+ "name, "
//					+ "description, "
//				+ "FROM items AS i"
//				+ "WHERE id = ";
//		
//		
//		return null;
//	}
//}
