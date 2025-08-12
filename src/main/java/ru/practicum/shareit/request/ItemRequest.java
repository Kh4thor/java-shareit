package ru.practicum.shareit.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.user.mvc.model.User;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ItemRequest {
	@Positive
	private Long id;
	private String description;
	private User requestor;
	private LocalDateTime created;
}
