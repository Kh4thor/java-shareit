package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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
public class UpdateUserDto {

	private Long userId;
	private String name;
	@Email
	private String email;

}
