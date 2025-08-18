package ru.practicum.shareit.booking.mvc.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.booking.utills.BookingStatus;
import ru.practicum.shareit.item.mvc.model.Item;
import ru.practicum.shareit.user.mvc.model.User;

@Entity
@Getter
@Setter
@Builder
@ToString
@Table(name = "booking")
public class Booking {
	@Id
	@Positive
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_id")
	private Long id;

	@Column(name = "booking_start")
	private LocalDateTime start;

	@Column(name = "booking_end")
	private LocalDateTime end;

	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User booker;
	private BookingStatus status;
}
