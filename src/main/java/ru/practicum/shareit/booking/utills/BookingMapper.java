package ru.practicum.shareit.booking.utills;


import ru.practicum.shareit.booking.mvc.model.Booking;
import ru.practicum.shareit.booking.mvc.model.dto.CreateBookingDto;
import ru.practicum.shareit.booking.mvc.model.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.mvc.model.dto.UpdateBookingDto;

public class BookingMapper {

	public static Booking createBookingDtoToBooking(CreateBookingDto createBookingDto) {
		 return Booking.builder()
				 .start(createBookingDto.getStart())
				 .end(createBookingDto.getEnd())
				 .build();
	}
	
	public static Booking updateBookingDtoToBooking(UpdateBookingDto updateBookingDto) {
		return Booking.builder()
				.id(updateBookingDto.getId())
				.start(updateBookingDto.getStart())
				.end(updateBookingDto.getEnd())
				.build();
	}

	public static ResponseBookingDto bookingToResponseBookingDto(Booking booking) {
		return	ResponseBookingDto.builder()
				.bookerId(booking.getBooker().getId())
				.itemId(booking.getItem().getId())
				.start(booking.getStart())
				.end(booking.getEnd())
				.build();
	}
}
