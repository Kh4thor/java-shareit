package ru.practicum.shareit.booking.model;

import java.time.LocalDateTime;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.ext.Booker;

public class Booking {
	private Long id;
	private LocalDateTime start;
	private LocalDateTime end;
	private Item item;
	private Booker booker;
	private BookingStatus bookingStatus;
	
	
	
	
	
}
