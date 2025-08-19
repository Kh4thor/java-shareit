package ru.practicum.shareit.item.utills;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ResponseCommentDto;
import ru.practicum.shareit.item.dto.ResponseItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.mvc.model.Comment;
import ru.practicum.shareit.item.mvc.model.Item;

@Slf4j
@Component
public class ItemMapper {

	public static ResponseItemDto itemToResponseItemDto(Item item) {
		
		List<Comment> commentsList = item.getComments();

		List<ResponseCommentDto> commentsDto = commentsList==null ? Collections.emptyList() :
				commentsList.stream().map(CommentMapper::commentToResponseCommentDto).toList();

		return	ResponseItemDto.builder()
				.id(item.getId())
				.name(item.getName())
				.comments(commentsDto)
				.available(item.getAvailable())
				.ownerId(item.getOwner().getId())
				.description(item.getDescription())
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
		
		public static ItemDto itemToItemDto(Item item) {
			
			return	ItemDto.builder()
					.id(item.getId())
					.name(item.getName())
					.build();
		}
}
