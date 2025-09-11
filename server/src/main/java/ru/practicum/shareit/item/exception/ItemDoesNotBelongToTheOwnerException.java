package ru.practicum.shareit.item.exception;

import lombok.Getter;

@Getter
public class ItemDoesNotBelongToTheOwnerException extends RuntimeException {

	private final Long itemId;
	private final Long ownerId;
	private final String errorMessage;

	public ItemDoesNotBelongToTheOwnerException(Long itemId, Long ownerId, String errorMessage) {
		super("Предмет id=" + itemId + " не принадлежит пользователю id=" + ownerId);
		this.itemId = itemId;
		this.ownerId = ownerId;
		this.errorMessage = errorMessage;
	}
}
