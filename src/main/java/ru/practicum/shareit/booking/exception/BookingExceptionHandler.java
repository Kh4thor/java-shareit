package ru.practicum.shareit.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ru.practicum.shareit.item.exception.ItemDoesNotBelongToTheOwnerException;

@RestControllerAdvice
public class BookingExceptionHandler {

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public BookingErrorResponse bookingNotFoundExceptionHandler(final BookingNotFoundException exception) {
		return new BookingErrorResponse(exception.getErrorMessage(), exception.getMessage());
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public BookingErrorResponse itemDoesNotBelongToTheOwnerException(
			final ItemDoesNotBelongToTheOwnerException exception) {
		return new BookingErrorResponse(exception.getErrorMessage(), exception.getMessage());
	}
}
