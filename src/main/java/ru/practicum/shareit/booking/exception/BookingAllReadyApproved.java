package ru.practicum.shareit.booking.exception;

import lombok.Getter;

@Getter
public class BookingAllReadyApproved extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorMessage;

	public BookingAllReadyApproved(Long bookingId, String errorMessage) {
		super("Бронирование id=" + bookingId + " уже подтверждено");
		this.errorMessage = errorMessage;
	}

}
