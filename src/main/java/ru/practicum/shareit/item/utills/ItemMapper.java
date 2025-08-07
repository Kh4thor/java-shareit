package ru.practicum.shareit.item.utills;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ResponseItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.mvc.model.Item;

@Slf4j
@Component
public class ItemMapper {

	public static ResponseItemDto itemToResponseItemDto(Item item) {
		return	ResponseItemDto.builder()
				.id(item.getId())
				.name(item.getName())
				.description(item.getDescription())
				.available(item.getAvailable())
				.ownerId(item.getOwner().getId())
				.itemRequestId(item.getItemRequest() != null ? item.getItemRequest().getId() : null)
				.build();
		}

		public static Item createItemDtoToItem(CreateItemDto createItemDto) {

			return	Item.builder()
					.id(0L)
					.name(createItemDto.getName())
					.description(createItemDto.getDescription())
					.available(createItemDto.getAvailable())
					.build();
		}

		public static Item updateItemDtoToItem(UpdateItemDto updateItemDto) {

			return	Item.builder()
					.id(updateItemDto.getItemId())
					.name(updateItemDto.getName())
					.description(updateItemDto.getDescription())
					.available(updateItemDto.getAvailable())
					.build();
		}
}
