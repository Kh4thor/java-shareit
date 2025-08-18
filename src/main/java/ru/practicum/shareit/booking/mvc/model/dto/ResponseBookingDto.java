package ru.practicum.shareit.booking.mvc.model.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ResponseBookingDto {

	private Long bookerId;
	private Long itemId;
	private LocalDateTime start;
	private LocalDateTime end;
}
