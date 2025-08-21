package ru.practicum.shareit.booking.exception;

import lombok.Getter;

@Getter
public class BookingAlreadyApproved extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorMessage;

	public BookingAlreadyApproved(Long bookingId, String errorMessage) {
		super("Бронирование id=" + bookingId + " уже подтверждено");
		this.errorMessage = errorMessage;
	}

}
