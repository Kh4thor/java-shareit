package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.request.ItemRequest;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class CreateItemDto {
	private String name;
	private String description;
	private Boolean available;
	private ItemRequest itemRequest;
}
