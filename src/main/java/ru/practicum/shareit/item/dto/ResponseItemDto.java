package ru.practicum.shareit.item.dto;

import java.util.List;

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
public class ResponseItemDto {
	private Long id;
	private String name;
	private String description;
	private Boolean available;
	private Long itemRequestId;
	private Long ownerId;
	private List<ResponseCommentDto> comments;
	private Long nextBooking;
	private Long lastBooking;

}
