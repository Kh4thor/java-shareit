package ru.practicum.shareit.user.exception;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final  Long userId;
	private final String errorMessage;

	public UserAlreadyExistsException(Long userId, String errorMessage) {
		super("Пользователь id=" + userId + " уже сущствует");
		this.userId = userId;
		this.errorMessage = errorMessage;
	}
}
