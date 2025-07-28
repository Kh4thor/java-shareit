package ru.practicum.shareit.user.exception;

public class EmailAllreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String email;
	private String errorMessage;

	public EmailAllreadyExistsException(String email, String errorMessage) {
		super("Запись email=" + email + " уже существует");
		this.email = email;
		this.errorMessage = errorMessage;
	}

	public String getEmail() {
		return email;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
