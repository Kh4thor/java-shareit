package ru.practicum.shareit.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public BookingErrorResponse handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
		String message = "Параметр '" + exception.getPropertyName() + "' имеет неверный формат.";
		return new BookingErrorResponse(message, exception.getMessage());
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public BookingErrorResponse handleWrongBookingStatusException(WrongBookingStatusException exception) {
		return new BookingErrorResponse(exception.getErrorMessage(), exception.getMessage());
	}
}
