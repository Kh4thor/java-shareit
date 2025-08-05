package ru.practicum.shareit.item.utills;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ResponseItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.mvc.model.Item;
import ru.practicum.shareit.user.mvc.controller.service.impl.UserService;
import ru.practicum.shareit.user.mvc.model.User;

@Slf4j
@Component
public class ItemMapper {

	private final UserService userService;

	public ItemMapper(UserService userService) {
		this.userService = userService;
	}

	public ResponseItemDto itemToResponseItemDto(Item item) {
		return	ResponseItemDto.builder()
				.id(item.getId())
				.name(item.getName())
				.description(item.getDescription())
				.available(item.getAvailable())
				.ownerId(item.getOwner().getId())
				.itemRequestId(item.getItemRequest() != null ? item.getItemRequest().getId() : null)
				.build();
		}

		public Item createItemDtoToItem(CreateItemDto createItemDto) {
			User owner = userService.getUser(createItemDto.getOwnerId());

			return	Item.builder()
					.id(0L)
					.name(createItemDto.getName())
					.description(createItemDto.getDescription())
					.available(createItemDto.getAvailable())
					.owner(owner)
					.build();
		}

		public Item updateItemDtoToItem(UpdateItemDto updateItemDto) {
			User owner = userService.getUser(updateItemDto.getOwnerId());

			return	Item.builder()
					.id(updateItemDto.getItemId())
					.name(updateItemDto.getName())
					.description(updateItemDto.getDescription())
					.available(updateItemDto.getAvailable())
					.owner(owner)
					.build();
		}
}
