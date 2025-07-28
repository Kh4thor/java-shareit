package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.item.utills.ItemStatus;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class ItemDto {
	private String name;
	private String description;
	private ItemStatus itemStatus;
	private Long itemRequestId;
}
