package ru.practicum.shareit.user.mvc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@RequiredArgsConstructor
public class User {
	@Positive
	private Long id;
	@NotBlank
	private String name;
	@NotBlank
	private String email;

	public User(Long id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}
}
