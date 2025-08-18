package ru.practicum.shareit.booking.utills;


import ru.practicum.shareit.booking.mvc.model.Booking;
import ru.practicum.shareit.booking.mvc.model.dto.CreateBookingDto;
import ru.practicum.shareit.booking.mvc.model.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.mvc.model.dto.UpdateBookingDto;
import ru.practicum.shareit.user.mvc.model.User;

public class BookingMapper {

	public static Booking createBookingDtoToBooking(CreateBookingDto createBookingDto) {
		 return Booking.builder()
				 .start(createBookingDto.getStart())
				 .end(createBookingDto.getEnd())
				 .status(BookingStatus.WAITING)
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

		User booker = booking.getBooker();
//		Long bookerId = booker == null ? null : booker.getId();

		return	ResponseBookingDto.builder()
//				.bookerId(bookerId)
				.booker(booker)
				.itemId(booking.getItem().getId())
				.itemName(booking.getItem().getName())
				.start(booking.getStart())
				.end(booking.getEnd())
				.status(booking.getStatus())
				.itemAvailable(booking.getItem().getAvailable())
				.build();
	}
}
