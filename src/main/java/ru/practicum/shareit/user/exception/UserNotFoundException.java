package ru.practicum.shareit.user.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Long userId;
	private String errorMessage;

	public UserNotFoundException(Long userId, String errorMessage) {
		super("Пользователь id=" + userId + " не найден");
		this.userId = userId;
		this.errorMessage = errorMessage;
	}
}