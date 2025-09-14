package ru.practicum.shareit.user.model;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class User {

	private Long id;

	private String name;

	private String email;
}
