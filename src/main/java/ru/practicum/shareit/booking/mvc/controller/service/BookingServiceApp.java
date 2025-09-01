package ru.practicum.shareit.booking.mvc.controller.service;

import java.util.List;

import ru.practicum.shareit.booking.mvc.model.dto.CreateBookingDto;
import ru.practicum.shareit.booking.mvc.model.dto.ParamsDto;
import ru.practicum.shareit.booking.mvc.model.dto.ResponseBookingDto;

public interface BookingServiceApp {

	ResponseBookingDto createBooking(CreateBookingDto createBookingDto);

	ResponseBookingDto getBooking(Long bookingId);

	ResponseBookingDto setApprove(ParamsDto paramsDto);

	List<ResponseBookingDto> getAllBookingsOfUser(ParamsDto paramsDto);

	List<ResponseBookingDto> getAllBookingsOfOwner(ParamsDto paramsDto);

	ResponseBookingDto getBookingOfOwner(ParamsDto paramsDto);

	void deleteBooking(ParamsDto paramsDto);

}