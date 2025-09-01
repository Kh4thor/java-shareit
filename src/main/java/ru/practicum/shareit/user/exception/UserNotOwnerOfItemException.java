package ru.practicum.shareit.user.exception;

import lombok.Getter;

@Getter
public class UserNotOwnerOfItemException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Long userId;
	private Long itemId;
	private String errorMessage;

	public UserNotOwnerOfItemException(Long userId, Long itemId, String errorMessage) {
		super("Пользователь id=" + userId + " не является владельцем предмета id=" + itemId);
		this.userId = userId;
		this.itemId = itemId;
		this.errorMessage = errorMessage;
	}
}
