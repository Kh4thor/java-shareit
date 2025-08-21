package ru.practicum.shareit.booking.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookingErrorResponse {
	private final String errorMessage;
	private final String description;
}