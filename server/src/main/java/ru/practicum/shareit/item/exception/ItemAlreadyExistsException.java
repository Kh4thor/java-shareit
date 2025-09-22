package ru.practicum.shareit.item.exception;

import lombok.Getter;

@Getter
public class ItemAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final Long itemId;
	private final String errorMessage;

	public ItemAlreadyExistsException(Long itemId, String errorMessage) {
		super("Предмет id=" + itemId + " уже существует");
		this.itemId = itemId;
		this.errorMessage = errorMessage;
	}
}
