package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.booking.dto.CreateBookingDto;

@Slf4j
@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> createBooking(
            @RequestHeader("X-Sharer-User-Id") @Positive @NotNull Long bookerId,
            @Valid @RequestBody CreateBookingDto createBookingDto) {
        return bookingClient.createBooking(bookerId, createBookingDto);
    }

    @PatchMapping("/{id}")
    ResponseEntity<Object> approveBooking(
            @PathVariable("id") @Positive @NotNull Long bookingId,
            @RequestParam("approved") @NotNull Boolean approved,
            @RequestHeader("X-Sharer-User-Id") @Positive @NotNull Long ownerId) {
        return bookingClient.approveBooking(bookingId, approved, ownerId);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteBooking(
            @PathVariable("id") Long bookingId,
            @RequestHeader("X-Sharer-User-Id") @Positive @NotNull Long ownerId) {
        return bookingClient.deleteBooking(bookingId, ownerId);
    }

    @GetMapping
    ResponseEntity<Object> getAllBookingsOfUser(
            @RequestHeader("X-Sharer-User-Id") @Positive @NotNull Long userId,
            @RequestParam(name = "state", defaultValue = "ALL") String state) {
        return bookingClient.getAllBookingsOfUser(userId, state);
    }

    @GetMapping("/owner")
    ResponseEntity<Object> getAllBookingsOfOwner(
            @RequestHeader("X-Sharer-User-Id") @Positive @NotNull Long ownerId,
            @RequestParam(name = "state", defaultValue = "ALL") String state) {
        return bookingClient.getAllBookingsOfOwner(ownerId, state);
    }

    @GetMapping("/{id}")
    ResponseEntity<Object> getBooking(
            @PathVariable("id") Long bookingId,
            @RequestHeader("X-Sharer-User-Id") @Positive @NotNull Long userId) {
        return bookingClient.getBooking(bookingId, userId);
    }
}