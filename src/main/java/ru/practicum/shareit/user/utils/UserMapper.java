package ru.practicum.shareit.user.utils;

import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.ResponseUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.mvc.model.User;

public class UserMapper {

	public static User createUserDtoToUser(CreateUserDto createUserDto) {
		return	User.builder()
				.id(null)
				.name(createUserDto.getName())
				.email(createUserDto.getEmail())
				.build();
	}
	
	public static User updateUserDtoUser (UpdateUserDto udateUserDto) {
		return	User.builder()
				.id(udateUserDto.getUserId())
				.name(udateUserDto.getName())
				.email(udateUserDto.getEmail())
				.build();
	}
	
	public static ResponseUserDto userToResponseUserDto(User user) {
		return	ResponseUserDto.builder()
				.id(user.getId())
				.name(user.getName())
				.email(user.getEmail())
				.build();
	}
}
