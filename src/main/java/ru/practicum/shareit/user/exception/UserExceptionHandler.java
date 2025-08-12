package ru.practicum.shareit.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public UserErrorResponse userNotFoundExceptionHandler(final UserNotFoundException exception) {
		return new UserErrorResponse(exception.getErrorMessage(), exception.getMessage());
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public UserErrorResponse emailAllreadyExistsExceptionHandler(final EmailAllreadyExistsException exception) {
		return new UserErrorResponse(exception.getErrorMessage(), exception.getMessage());
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public UserErrorResponse userAlreadyExistsExceptionHandler(final UserAlreadyExistsException exception) {
		return new UserErrorResponse(exception.getErrorMessage(), exception.getMessage());
	}
}
