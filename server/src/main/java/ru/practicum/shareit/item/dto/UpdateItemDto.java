package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.Positive;
import lombok.*;

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
