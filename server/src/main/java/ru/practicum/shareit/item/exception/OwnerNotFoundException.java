package ru.practicum.shareit.item.exception;

import lombok.Getter;

@Getter
public class OwnerNotFoundException extends RuntimeException {

	private final Long ownerId;
	private final String errorMessage;

	public OwnerNotFoundException(Long ownerId, String errorMessage) {
		super("Не найден владелец id:" + ownerId);
		this.ownerId = ownerId;
		this.errorMessage = errorMessage;
	}
}
