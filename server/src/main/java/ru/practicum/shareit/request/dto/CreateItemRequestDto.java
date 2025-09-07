package ru.practicum.shareit.request.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
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