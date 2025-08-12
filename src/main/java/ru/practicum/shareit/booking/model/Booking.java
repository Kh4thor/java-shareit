package ru.practicum.shareit.booking.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.item.mvc.model.Item;
import ru.practicum.shareit.user.mvc.model.User;

@Getter
@Setter
@ToString
public class Booking {
	private Long id;
	private LocalDateTime start;
	private LocalDateTime end;
	private Item item;
	private User booker;
	private BookingStatus bookingStatus;
}
