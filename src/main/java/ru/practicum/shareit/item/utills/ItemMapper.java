//package ru.practicum.shareit.item.utills;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import org.springframework.jdbc.core.RowMapper;
//
//import ru.practicum.shareit.item.dto.ItemDto;
//import ru.practicum.shareit.item.model.Item;
//import ru.practicum.shareit.owner.Owner;
//
//public class ItemMapper implements RowMapper<Item> {
//
//	private final OwnerService ownerService;
//
//	public ItemMapper(OwnerService ownerService) {
//		this.ownerService = ownerService;
//	}
//
//
//	public static ItemDto toItemDto(Item item) {
//		
//		return	ItemDto.builder()
//				.name(item.getName())
//				.description(item.getDescription())
//				.itemStatus(item.getItemStatus())
//				.itemRequestId(item.getRequest() != null ? item.getRequest().getId() : null).build();
//		}
//	
////	private Long id;
////	private String name;
////	private String description;
////	private ItemStatus itemStatus;
////	private Owner owner;
////	private ItemRequest request;
//
//	
//
//	@Override
//	public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
//		
//		int ownerId = rs.getInt("owner_id");
//		Owner owner = ownerService.getOwner(ownerId);
//		
//	return	Item.builder()
//			.id(rs.getLong("id"))
//			.name(rs.getString("name"))
//			.description(rs.getString("description"))
//			.itemStatus(null)
//			.owner(owner)
//			.request(null)
//			.build();
//	}
//	
//}
