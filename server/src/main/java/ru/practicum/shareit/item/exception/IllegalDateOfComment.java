package ru.practicum.shareit.item.exception;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class IllegalDateOfComment extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final LocalDateTime endOfBooking;
    private final LocalDateTime commentCreated;
    private final String errorMessage;
    private final Long bookingId;

    public IllegalDateOfComment(LocalDateTime commentCreated, Long bookingId, LocalDateTime endOfBooking, String errorMessage) {
        super("Время создания комментария: " + commentCreated + " не может быть раньше окончания бронирования id=:" + bookingId + endOfBooking);
        this.endOfBooking = endOfBooking;
        this.errorMessage = errorMessage;
        this.commentCreated = commentCreated;
        this.bookingId = bookingId;
    }
}
