package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.item.utills.ItemStatus;
import ru.practicum.shareit.owner.Owner;
import ru.practicum.shareit.request.ItemRequest;

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
	private Owner owner;
	private ItemRequest request;
}
