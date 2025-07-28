package ru.practicum.shareit.item.mvc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.item.utills.ItemStatus;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.mvc.model.User;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Item {
	
	private Long id;
	private String name;
	private String description;
	private ItemStatus itemStatus;
	private User owner;
	private ItemRequest itemRequest;
}
