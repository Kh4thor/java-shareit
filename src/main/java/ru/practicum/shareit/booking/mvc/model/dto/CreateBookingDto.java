package ru.practicum.shareit.booking.mvc.model.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class CreateBookingDto {

	private Long ownerId;

	private Long bookerId;

	@NotNull
	@Positive
	private Long itemId;
	private LocalDateTime start;
	private LocalDateTime end;
}
