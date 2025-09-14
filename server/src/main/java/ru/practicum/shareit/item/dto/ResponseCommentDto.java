package ru.practicum.shareit.item.dto;

import lombok.*;

import java.time.LocalDateTime;

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
