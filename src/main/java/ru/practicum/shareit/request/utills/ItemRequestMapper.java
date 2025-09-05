package ru.practicum.shareit.request.utills;

import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ResponseItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;

public class ItemRequestMapper {
	public static ItemRequest createItemRequestDtoToItemRequest(CreateItemRequestDto createItemRequestDto) {
		return ItemRequest.builder()
				.description(createItemRequestDto.getDescription())
				.build();
	}

	public static ResponseItemRequestDto itemRequestToResponseItemRequestDto(ItemRequest itemRequest) {
		return ResponseItemRequestDto.builder()
				.id(itemRequest.getId())
				.description(itemRequest.getDescription())
				.requestorId(itemRequest.getRequestor().getId())
				.created(itemRequest.getCreated())
				.build();
	}
}