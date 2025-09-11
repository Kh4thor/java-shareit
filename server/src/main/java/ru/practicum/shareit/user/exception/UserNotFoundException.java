package ru.practicum.shareit.user.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final Long userId;
    private final String errorMessage;

    public UserNotFoundException(Long userId, String errorMessage) {
        super("Пользователь id=" + userId + " не найден");
        this.userId = userId;
        this.errorMessage = errorMessage;
    }
}