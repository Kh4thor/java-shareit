package ru.practicum.shareit.item.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemErrorResponse {

	private final String errorMessage;
	private final String description;
}