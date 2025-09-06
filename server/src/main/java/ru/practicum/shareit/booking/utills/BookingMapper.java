package ru.practicum.shareit.booking.utills;


import ru.practicum.shareit.booking.mvc.model.Booking;
import ru.practicum.shareit.booking.mvc.model.dto.CreateBookingDto;
import ru.practicum.shareit.booking.mvc.model.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.mvc.model.dto.UpdateBookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mvc.model.Item;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mvc.model.User;

public class BookingMapper {

	public static Booking createBookingDtoToBooking(CreateBookingDto createBookingDto) {
		 return Booking.builder()
				 .start(createBookingDto.getStart())
				 .end(createBookingDto.getEnd())
				 .status(createBookingDto.getBookingStatus())
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
		UserDto bookerDto =	UserDto.builder()
							.id(booker.getId())
							.build();

		Item item = booking.getItem();
		ItemDto itemDto =	ItemDto.builder()
				.id(item.getId()).name(item.getName())
							.build();

		return	ResponseBookingDto.builder()
				.id(booking.getId())
				.booker(bookerDto).item(itemDto)
				.start(booking.getStart())
				.end(booking.getEnd())
				.status(booking.getStatus())
				.build();
	}
}
