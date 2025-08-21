package ru.practicum.shareit.item.exception;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class IllegalDateOfComment extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private LocalDateTime endOfBooking;
	private LocalDateTime commentCreated;
	private String errorMessage;

	public IllegalDateOfComment(LocalDateTime commentCreated, LocalDateTime endOfBooking, String errorMessage) {
		super("Дата создания комментария: " + commentCreated + " не может быть раньше окончания бронирования: " + endOfBooking);
		this.endOfBooking = endOfBooking;
		this.errorMessage = errorMessage;
		this.commentCreated = commentCreated;
	}

}
