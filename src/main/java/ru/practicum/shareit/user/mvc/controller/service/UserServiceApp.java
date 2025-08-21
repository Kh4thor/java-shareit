package ru.practicum.shareit.user.mvc.controller.service;

import java.util.List;

import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.ResponseUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;

public interface UserServiceApp {

	ResponseUserDto createUser(CreateUserDto createUserDto);

	ResponseUserDto updateUser(UpdateUserDto updateUserDto);

	ResponseUserDto getUser(Long userId);

	void deleteUser(Long userId);

	List<ResponseUserDto> getAllUsers();

	void deleteAllUsers();

}