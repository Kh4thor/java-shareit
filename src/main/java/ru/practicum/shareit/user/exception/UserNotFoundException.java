package ru.practicum.shareit.user.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Long userId;
	private String errorMessage;

	public UserNotFoundException(Long userId, String errorMessage) {
		super("Пользователь с id=" + userId + " не найден");
		this.userId = userId;
		this.errorMessage = errorMessage;
	}

	public Long getUserId() {
		return userId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}