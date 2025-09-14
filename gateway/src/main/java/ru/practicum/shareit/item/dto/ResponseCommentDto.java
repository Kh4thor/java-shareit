package ru.practicum.shareit.item.dto;

import java.time.LocalDateTime;

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
public class ResponseCommentDto {
	private Long id;
	private Long authorId;
	private String authorName;
	private Long itemId;
	private String text;
	private LocalDateTime created;
}
