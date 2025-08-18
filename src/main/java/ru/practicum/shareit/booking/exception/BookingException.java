package ru.practicum.shareit.booking.exception;

import org.springframework.stereotype.Component;

import ru.practicum.shareit.booking.mvc.controller.repository.BookingRepositoryApp;

@Component
public class BookingException {

	private final BookingRepositoryApp bookingRepository;

	public BookingException(BookingRepositoryApp bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	public void checkBookingNotFoundException(Long bookingId, String errorMessage) {
		if (!bookingRepository.isBookingExists(bookingId)) {
			throw new BookingNotFoundException(bookingId, errorMessage);
		}
	}

}
