package ru.practicum.shareit.booking.exception;

import lombok.Getter;

@Getter
public class BookingNotFoundException extends RuntimeException {

	private final Long bookingId;
	private final String errorMessage;

	public BookingNotFoundException(Long bookingId, String errorMessage) {
		super("Бронирование id=" + bookingId + " не найдено");
		this.bookingId = bookingId;
		this.errorMessage = errorMessage;
	}
}
