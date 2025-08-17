package ru.practicum.shareit.item.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.item.mvc.model.Item;

@Getter
@Setter
@Builder
@ToString
public class ResponseCommentDto {
	private Long id;
	private Long commentatorId;
	private Item itemId;
	private String text;
	private LocalDateTime created;
}
