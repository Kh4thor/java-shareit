package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class UpdateUserDto {

	private Long userId;
	private String name;
	@Email
	private String email;

}
