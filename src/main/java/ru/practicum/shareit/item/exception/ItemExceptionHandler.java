package ru.practicum.shareit.item.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ItemExceptionHandler {

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ItemErrorResponse itemNotFoundExceptionHandler(final ItemNotFoundException exception) {
		return new ItemErrorResponse(exception.getErrorMessage(), exception.getMessage());
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public ItemErrorResponse itemAlreadyExistsExceptionHandler(final ItemAlreadyExistsException exception) {
		return new ItemErrorResponse(exception.getErrorMessage(), exception.getMessage());
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public ItemErrorResponse itemAlreadyBelongsToTheOwnerExceptionHandler(final ItemAlreadyBelongsToTheOwnerException exception) {
		return new ItemErrorResponse(exception.getErrorMessage(), exception.getMessage());
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public ItemErrorResponse itemDoesNotBelongToTheOwnerExceptionHandler(final ItemDoesNotBelongToTheOwnerException exception) {
		return new ItemErrorResponse(exception.getErrorMessage(), exception.getMessage());
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ItemErrorResponse ownerNotFoundException(final OwnerNotFoundException exception) {
		return new ItemErrorResponse(exception.getErrorMessage(), exception.getMessage());
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ItemErrorResponse ownerOfItemNotFoundException(final OwnerOfItemNotFoundException exception) {
		return new ItemErrorResponse(exception.getErrorMessage(), exception.getMessage());
	}
}
