package ru.practicum.shareit.booking.mvc.controller.service.impl;

import lombok.Getter;
import ru.practicum.shareit.item.mvc.model.Item;

@Getter
public class ItemIsUnavailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Long itemId;
	private Boolean itemStatus;
	private String errorMessage;

	public ItemIsUnavailableException(Item item, String errorMessage) {
		super("Предмет id=" + item.getId() + " недоступен. Статус предмета: " + item.getAvailable());
		this.itemId = item.getId();
		this.itemStatus = item.getAvailable();
		this.errorMessage = errorMessage;
	}
}
