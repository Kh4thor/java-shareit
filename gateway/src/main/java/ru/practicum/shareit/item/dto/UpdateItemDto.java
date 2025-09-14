package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class UpdateItemDto {

	@Positive
	private Long itemId;
	private String name;
	private String description;
	private Boolean available;
	@Positive
	private Long ownerId;
	@Positive
	private Long itemRequestId;
}
