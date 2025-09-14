package ru.practicum.shareit.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class ResponseUserDto {

	private Long id;
	private String name;
	private String email;
}
