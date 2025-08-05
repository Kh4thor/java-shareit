package ru.practicum.shareit.user.mvc.controller.service;

import java.util.List;

import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.mvc.model.User;

public interface UserServiceApp {

	User createUser(CreateUserDto createUserDto);

	User updateUser(UpdateUserDto updateUserDto);

	User getUser(Long userId);

	User deleteUser(Long userId);

	List<User> getAllUsers();

	List<User> deleteAllUsers();

}