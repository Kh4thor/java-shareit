package ru.practicum.shareit.booking.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.booker.model.Booker;
import ru.practicum.shareit.item.model.Item;

@Getter
@Setter
@ToString
public class Booking {
	private Long id;
	private LocalDateTime start;
	private LocalDateTime end;
	private Item item;
	private Booker booker;
	private BookingStatus bookingStatus;
}
