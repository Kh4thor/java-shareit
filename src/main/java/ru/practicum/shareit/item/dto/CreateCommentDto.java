package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class CreateCommentDto {
	private Long commentatorId;
	private Long itemId;
	private String text;
}