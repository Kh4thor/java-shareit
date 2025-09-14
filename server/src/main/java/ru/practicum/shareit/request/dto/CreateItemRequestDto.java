package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateItemRequestDto {

	private Long requestId;

	@Positive
	private Long ownerId;

	@Positive
	private Long itemId;

	@NotBlank
	private String description;

	@PastOrPresent
	private LocalDateTime create;
}