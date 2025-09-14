package ru.practicum.shareit.booking.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
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
public class UpdateBookingDto {

	@NotNull
	@Positive
	private Long id;

	@NotNull
	@Positive
	private Long bookerId;

	@NotNull
	@Positive
	private Long itemId;
	private LocalDateTime start;
	private LocalDateTime end;
}
