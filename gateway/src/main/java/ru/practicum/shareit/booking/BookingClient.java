package ru.practicum.shareit.booking;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.shareit.booking.dto.CreateBookingDto;
import ru.practicum.shareit.booking.dto.ParamsDto;
import ru.practicum.shareit.booking.dto.ResponseBookingDto;


import java.util.List;

@FeignClient(name = "booking-client", url = "${shareit.server.url}")
public interface BookingClient {

    @PostMapping("/bookings")
    ResponseBookingDto createBooking(
            @RequestHeader("X-Sharer-User-Id") Long bookerId,
            @RequestBody CreateBookingDto createBookingDto);

    @PatchMapping("/bookings/{id}")
    ResponseBookingDto approveBooking(
            @PathVariable("id") Long bookingId,
            @RequestParam Boolean approved,
            @RequestHeader("X-Sharer-User-Id") Long ownerId);

    @DeleteMapping("/bookings/{id}")
    void deleteBooking(
            @PathVariable("id") Long bookingId,
            @RequestHeader("X-Sharer-User-Id") Long ownerId);

    @GetMapping("/bookings")
    List<ResponseBookingDto> getAllBookingsOfUser(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestParam(defaultValue = "ALL") String state);

    @GetMapping("/bookings/owner")
    List<ResponseBookingDto> getAllBookingsOfOwner(
            @RequestHeader("X-Sharer-User-Id") Long ownerId,
            @RequestParam(defaultValue = "ALL") String state);

    @GetMapping("/bookings/{id}")
    ResponseBookingDto getBooking(
            @PathVariable("id") Long bookingId,
            @RequestHeader("X-Sharer-User-Id") Long userId);
}