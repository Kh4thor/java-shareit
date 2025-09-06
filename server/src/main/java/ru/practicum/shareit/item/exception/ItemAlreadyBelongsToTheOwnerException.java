package ru.practicum.shareit.item.exception;

import lombok.Getter;

@Getter
public class ItemAlreadyBelongsToTheOwnerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Long itemId;
	private Long ownerId;
	private String errorMessage;

	public ItemAlreadyBelongsToTheOwnerException(Long itemId, Long ownerId, String errorMessage) {
		super("Предмет id=" + itemId + " уже принадлежит пользователю id=" + ownerId);
		this.itemId = itemId;
		this.ownerId = ownerId;
		this.errorMessage = errorMessage;
	}
}
