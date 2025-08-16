package ru.practicum.shareit.user.mvc.controller.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.ResponseUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;

public interface UserControllerApp {

	ResponseUserDto createUser(CreateUserDto createUserDto);

	ResponseUserDto updateUser(UpdateUserDto updateUserDto, Long userId);

	ResponseUserDto getUser(Long userId);

	void deleteUser(Long userId);

	List<ResponseUserDto> getAllUsers();

	void deleteAllUsers();

}