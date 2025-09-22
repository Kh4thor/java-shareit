package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.CreateBookingDto;
import ru.practicum.shareit.booking.dto.ParamsDto;
import ru.practicum.shareit.booking.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingService {
    ResponseBookingDto createBooking(CreateBookingDto createBookingDto);

    ResponseBookingDto getBooking(Long bookingId);

    void deleteBooking(ParamsDto paramsDto);

    Booking createBookingDtoToBooking(CreateBookingDto createBookingDto, String errorMessage);

    ResponseBookingDto setApprove(ParamsDto paramsDto);

    List<ResponseBookingDto> getAllBookingsOfUser(ParamsDto paramsDto);

    List<ResponseBookingDto> getAllBookingsOfOwner(ParamsDto paramsDto);

    ResponseBookingDto getBookingOfOwner(ParamsDto paramsDto);
}
