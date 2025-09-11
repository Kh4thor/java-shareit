package ru.practicum.shareit.item.exception;

import lombok.Getter;

@Getter
public class ItemNotFoundException extends RuntimeException {

	private final Long itemId;
	private final String errorMessage;

	public ItemNotFoundException(Long itemId, String errorMessage) {
		super("Предмет id=" + itemId + " не найден");
		this.itemId = itemId;
		this.errorMessage = errorMessage;
	}
}
