package ru.practicum.shareit.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.booker.model.Booker;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class ItemRequest {
	private Long id;
	private String description;
	private Booker requestor;
	private LocalDateTime created;
}
