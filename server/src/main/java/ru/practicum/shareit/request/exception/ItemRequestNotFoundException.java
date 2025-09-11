package ru.practicum.shareit.request.exception;

import lombok.Getter;

@Getter
public class ItemRequestNotFoundException extends RuntimeException {

	private final String errorMessage;
	private final Long itemRequestId;

	public ItemRequestNotFoundException(Long itemRequestId, String errorMessage) {
		super("Запрос на бронирование id=" + itemRequestId + "не найден");
		this.errorMessage = errorMessage;
		this.itemRequestId = itemRequestId;
	}
}
