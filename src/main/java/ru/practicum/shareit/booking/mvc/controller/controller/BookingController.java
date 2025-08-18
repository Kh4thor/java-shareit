package ru.practicum.shareit.booking.mvc.controller.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.practicum.shareit.booking.mvc.controller.service.impl.BookingService;
import ru.practicum.shareit.booking.mvc.model.dto.CreateBookingDto;
import ru.practicum.shareit.booking.mvc.model.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.mvc.model.dto.UpdateBookingDto;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

	private final BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@PostMapping
	public ResponseBookingDto createBooking(
			@RequestHeader("X-Sharer-User-Id") Long ownerId,
			@Validated @RequestBody CreateBookingDto createBookingDto) {

		createBookingDto.setOwnerId(ownerId);

		return bookingService.createBooking(createBookingDto);
	}

	@PatchMapping("/{id}")
	public ResponseBookingDto updateBooking(
			@RequestHeader("X-Sharer-User-Id") Long bookerId,
			@PathVariable("id") Long bookingId,
			@Validated @RequestBody UpdateBookingDto updateBookingDto) {

		updateBookingDto.setBookerId(bookerId);
		updateBookingDto.setId(bookingId);

		return bookingService.updateBooking(updateBookingDto);
	}
	
	@DeleteMapping
	public void deleteAllBookings() {
		bookingService.deleteAllBookings();
	}
}
