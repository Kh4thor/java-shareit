package ru.practicum.shareit.item.exception;

import lombok.Getter;

@Getter
public class ItemDoesNotBelongToTheOwnerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Long itemId;
	private Long ownerId;
	private String errorMessage;

	public ItemDoesNotBelongToTheOwnerException(Long itemId, Long ownerId, String errorMessage) {
		super("Предмет id=" + itemId + " не принадлежит пользователю id=" + ownerId);
		this.itemId = itemId;
		this.ownerId = ownerId;
		this.errorMessage = errorMessage;
	}
}
