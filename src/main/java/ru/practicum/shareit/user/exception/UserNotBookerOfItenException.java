package ru.practicum.shareit.user.exception;

import lombok.Getter;

@Getter
public class UserNotBookerOfItenException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Long userId;
	private Long itemId;
	private String errorMessage;

	public UserNotBookerOfItenException(Long userId, Long itemId, String errorMessage) {
		super("Пользователь id=" + userId + " не бронировал предмет id=" + itemId);
		this.userId = userId;
		this.itemId = itemId;
		this.errorMessage = errorMessage;
	}
}
