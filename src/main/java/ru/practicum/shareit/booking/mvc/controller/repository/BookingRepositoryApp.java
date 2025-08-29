package ru.practicum.shareit.booking.mvc.controller.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.practicum.shareit.booking.mvc.model.Booking;
import ru.practicum.shareit.item.mvc.model.Item;

public interface BookingRepositoryApp extends JpaRepository<Booking, Long> {

	@Query("SELECT b FROM Booking b WHERE b.id = :bookingId")
	Optional<Booking> findById(@Param("bookingId") Long bookingId);

	@Query("SELECT b FROM Booking b WHERE b.booker.id = :userId "
			+ "AND ("
			+ "   :state = 'ALL' OR "
			+ "   (:state = 'CURRENT' AND CURRENT_TIMESTAMP BETWEEN b.start AND b.end) OR "
			+ "   (:state = 'PAST' AND b.end < CURRENT_TIMESTAMP) OR "
			+ "   (:state = 'FUTURE' AND b.start > CURRENT_TIMESTAMP) OR "
			+ "   (:state = 'WAITING' AND b.status = 'WAITING') OR "
			+ "   (:state = 'REJECTED' AND b.status = 'REJECTED')"
			+ ") "
			+ "ORDER BY b.start DESC")
	List<Booking> findByUserIdAndState(@Param("userId") Long userId, @Param("state") String state);

	@Query("SELECT b FROM Booking b WHERE b.item.owner.id = :ownerId "
			+ "AND ("
			+ "   :state = 'ALL' OR "
			+ "   (:state = 'CURRENT' AND CURRENT_TIMESTAMP BETWEEN b.start AND b.end) OR "
			+ "   (:state = 'PAST' AND b.end < CURRENT_TIMESTAMP) OR "
			+ "   (:state = 'FUTURE' AND b.start > CURRENT_TIMESTAMP) OR "
			+ "   (:state = 'WAITING' AND b.status = 'WAITING') OR "
			+ "   (:state = 'REJECTED' AND b.status = 'REJECTED')"
			+ ") "
			+ "ORDER BY b.start DESC")
	List<Booking> findByOwnerIdAndState(@Param("ownerId") Long ownerId, @Param("state") String state);

	@Query("SELECT b FROM Booking b WHERE b.item IN :items")
	List<Booking> findByItemIn(@Param("items") List<Item> items);
}