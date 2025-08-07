package ru.practicum.shareit.item.exception;

import lombok.Getter;

@Getter
public class ItemNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Long itemId;
	private String errorMessage;

	public ItemNotFoundException(Long itemId, String errorMessage) {
		super("Предмет id=" + itemId + " не найден");
		this.itemId = itemId;
		this.errorMessage = errorMessage;
	}

	public ItemNotFoundException(Long itemId) {
		super("Предмет id=" + itemId + " не найден");
		this.itemId = itemId;
	}

}
