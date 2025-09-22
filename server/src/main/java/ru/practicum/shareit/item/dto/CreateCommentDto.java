package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateCommentDto {
	private Long commentatorId;
	private Long itemId;

	@NotBlank
	private String text;
}