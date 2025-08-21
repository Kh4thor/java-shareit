package ru.practicum.shareit.booking.mvc.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.practicum.shareit.booking.mvc.model.Booking;

public interface BookingRepositoryApp extends JpaRepository<Booking, Long> {

	String isBookingExistsSql = ""
			+ "SELECT EXISTS (SELECT 1 "
							+ "FROM booking "
							+ "WHERE booking_id = :bookingId)";

	@Query(value = isBookingExistsSql, nativeQuery = true)
	boolean isBookingExists(@Param("bookingId") Long bookingId);
}
