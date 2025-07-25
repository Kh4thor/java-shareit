package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.ext.Owner;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class Item {
	
	private Long id;
	private String name;
	private String description;
	private ItemStatus itemStatus;
	private Owner owner;
	private ItemRequest request;
	
	
	
	
}
