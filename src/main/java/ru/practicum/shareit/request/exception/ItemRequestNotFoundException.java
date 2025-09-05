package ru.practicum.shareit.request.exception;

import lombok.Getter;

@Getter
public class ItemRequestNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorMessage;
	private Long itemRequestId;

	public ItemRequestNotFoundException(Long itemRequestId, String errorMessage) {
		super("Запрос на бронирование id=" + itemRequestId + "не найден");
		this.errorMessage = errorMessage;
		this.itemRequestId = itemRequestId;
	}
}
