package ru.practicum.shareit.user.exception;

import lombok.Getter;

@Getter
public class EmailAllreadyExistsException extends RuntimeException {

	private final String email;
	private final String errorMessage;

	public EmailAllreadyExistsException(String email, String errorMessage) {
		super("Запись email=" + email + " уже существует");
		this.email = email;
		this.errorMessage = errorMessage;
	}
}
