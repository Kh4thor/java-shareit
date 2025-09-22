package ru.practicum.shareit.booking.exception;

import lombok.Getter;
import ru.practicum.shareit.booking.utills.BookingStatus;

@Getter
public class WrongBookingStatusException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String errorMessage;
	private final BookingStatus currentValue;
	private final BookingStatus expectedValue;

	public WrongBookingStatusException(BookingStatus currentValue, BookingStatus expectedValue, String errorMessage) {
		super("Невенрый статус бронирования " + currentValue + ". Ожидался статус " + expectedValue);
		this.errorMessage = errorMessage;
		this.currentValue = currentValue;
		this.expectedValue = expectedValue;
	}
}
