package ru.practicum.shareit.booking.mvc.controller.service;

import ru.practicum.shareit.booking.mvc.model.dto.CreateBookingDto;
import ru.practicum.shareit.booking.mvc.model.dto.ResponseBookingDto;

public interface BookingServiceApp {
	ResponseBookingDto createBooking(CreateBookingDto createBookingDto);

	void deleteBooking(Long bookingId);

	void deleteAllBookings();
}
