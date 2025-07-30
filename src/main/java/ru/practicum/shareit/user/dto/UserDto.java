package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.user.annotation.uniqueEmail.UniqueEmail;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDto {
	@Positive
	private Long id;

	@NotBlank
	@Size(max = 50)
	private String name;

	@Email
	@NotBlank
	@UniqueEmail
	@Size(max = 50)
	private String email;
}
