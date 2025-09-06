package ru.practicum.shareit.item.exception;

import lombok.Getter;

@Getter
public class OwnerOfItemNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Long itemId;
	private String errorMessage;

	public OwnerOfItemNotFoundException(Long itemId, String errorMessage) {
		super("Не найден владелец предмета id=" + itemId);
		this.itemId = itemId;
		this.errorMessage = errorMessage;
	}

	public OwnerOfItemNotFoundException(Long itemId) {
		super("Не найден владелец предмета id=" + itemId);
		this.itemId = itemId;
	}
}
