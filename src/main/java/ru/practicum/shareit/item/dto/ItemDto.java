package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class ItemDto {
	private Long id;
	private String name;
	private String description;
	private Boolean itemStatus;
	private Long ownerId;
	private Long itemRequestId;
}
