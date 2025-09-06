package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NotNull
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateUserDto {

	@NotBlank
	@Size(max = 50)
	private String name;

	@Email
	@NotBlank
	@Size(max = 50)
	private String email;
}
