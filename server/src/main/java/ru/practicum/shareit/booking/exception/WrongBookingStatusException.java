package ru.practicum.shareit.booking.exception;

import lombok.Getter;
import ru.practicum.shareit.booking.utills.BookingStatus;

@Getter
public class WrongBookingStatusException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorMessage;

	public WrongBookingStatusException(BookingStatus currentValue, BookingStatus expectedValue, String errorMessage) {
		super("Невенрый статус бронирования " + currentValue + ". Ожидался статус " + expectedValue);
		this.errorMessage = errorMessage;
	}
}
