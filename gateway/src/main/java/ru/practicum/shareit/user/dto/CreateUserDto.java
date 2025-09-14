package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
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
