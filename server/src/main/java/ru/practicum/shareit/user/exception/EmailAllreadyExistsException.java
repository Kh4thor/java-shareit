package ru.practicum.shareit.user.exception;

import lombok.Getter;

@Getter
public class EmailAllreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String email;
	private String errorMessage;

	public EmailAllreadyExistsException(String email, String errorMessage) {
		super("Запись email=" + email + " уже существует");
		this.email = email;
		this.errorMessage = errorMessage;
	}
}
