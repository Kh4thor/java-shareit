package ru.practicum.shareit.item.exception;

import lombok.Getter;
import ru.practicum.shareit.item.mvc.model.Item;

@Getter
public class ItemIsUnavailableException extends RuntimeException {

	private final Long itemId;
	private final Boolean itemStatus;
	private final String errorMessage;

	public ItemIsUnavailableException(Item item, String errorMessage) {
		super("Предмет id=" + item.getId() + " недоступен. Статус предмета: " + item.getAvailable());
		this.itemId = item.getId();
		this.itemStatus = item.getAvailable();
		this.errorMessage = errorMessage;
	}
}
