package ru.practicum.shareit.booking.mvc.controller.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.practicum.shareit.booking.mvc.controller.service.impl.BookingService;
import ru.practicum.shareit.booking.mvc.model.dto.CreateBookingDto;
import ru.practicum.shareit.booking.mvc.model.dto.ResponseBookingDto;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

	private final BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@PostMapping
	public ResponseBookingDto createBooking(
			@RequestHeader("X-Sharer-User-Id") @Positive @NotNull Long bookerId,
			@Validated @RequestBody CreateBookingDto createBookingDto) {

		createBookingDto.setBookerId(bookerId);
		return bookingService.createBooking(createBookingDto);
	}

	@PatchMapping("/{id}")
	public ResponseBookingDto approveBooking(
			@PathVariable("id") Long bookingId,
			@RequestParam Boolean approved,
			@RequestHeader("X-Sharer-User-Id") @Positive @NotNull Long ownerId) {
		
		ApproveDto approveDto =	ApproveDto.builder()
								.bookingId(bookingId)
								.approve(approved)
								.ownerId(ownerId)
								.build();

		return bookingService.setApprove(approveDto);
	}

//	@DeleteMapping
//	public void deleteAllBookings() {
//		bookingService.deleteAllBookings();
//	}
}
