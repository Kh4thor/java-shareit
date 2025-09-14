package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Item {

	private Long id;

	private String name;

	private String description;

	private Boolean available;

	@ManyToOne
	private User owner;

	@ManyToOne
	private ItemRequest itemRequest;

	 @Override
	    public String toString() {
	        return "Item ["
	                + "id=" + id
	                + ", name=" + name + ", description=" + description
	                + ", available=" + available
	                + ", ownerId=" + (owner != null ? owner.getId() : "null")
	                + ", requestId=" + (itemRequest != null ? itemRequest.getId() : "null")
	                + "]";
	    }
}