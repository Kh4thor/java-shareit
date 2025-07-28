package ru.practicum.shareit.user.exception;


public class UserErrorResponse {
	private final String errorMessage;
	private final String description;

	public UserErrorResponse(String errorMessage, String description) {
		this.errorMessage = errorMessage;
		this.description = description;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getDescription() {
		return description;
	}
}