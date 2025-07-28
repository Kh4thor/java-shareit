package ru.practicum.shareit.user.exception;

public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Long userId;
	private String errorMessage;

	public UserAlreadyExistsException(Long userId, String errorMessage) {
		super("Пользователь с id=" + userId + " уже сущствует");
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
