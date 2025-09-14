package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

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
