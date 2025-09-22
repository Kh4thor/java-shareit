package ru.practicum.shareit.item.exception;

import lombok.Getter;

@Getter
public class ItemAlreadyBelongsToTheOwnerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final Long itemId;
	private final Long ownerId;
	private final String errorMessage;

	public ItemAlreadyBelongsToTheOwnerException(Long itemId, Long ownerId, String errorMessage) {
		super("Предмет id=" + itemId + " уже принадлежит пользователю id=" + ownerId);
		this.itemId = itemId;
		this.ownerId = ownerId;
		this.errorMessage = errorMessage;
	}
}
