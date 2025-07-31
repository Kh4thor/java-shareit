package ru.practicum.shareit.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserErrorResponse {
	private final String errorMessage;
	private final String description;
}