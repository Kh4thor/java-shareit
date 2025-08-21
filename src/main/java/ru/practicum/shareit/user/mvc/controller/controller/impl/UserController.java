package ru.practicum.shareit.user.mvc.controller.controller.impl;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.ResponseUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.mvc.controller.controller.UserControllerApp;
import ru.practicum.shareit.user.mvc.controller.service.UserServiceApp;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController implements UserControllerApp {

	private final UserServiceApp userService;

	public UserController(UserServiceApp userService) {
		this.userService = userService;
	}

	@Override
	@PostMapping
	public ResponseUserDto createUser(@Valid @RequestBody CreateUserDto createUserDto) {
		return userService.createUser(createUserDto);
	}

	@Override
	@PatchMapping("/{id}")
	public ResponseUserDto updateUser(@Valid @RequestBody UpdateUserDto updateUserDto, @PathVariable("id") Long userId) {
		updateUserDto.setUserId(userId);
		return userService.updateUser(updateUserDto);
	}

	@Override
	@GetMapping("/{id}")
	public ResponseUserDto getUser(@PathVariable("id") Long userId) {
		return userService.getUser(userId);
	}

	@Override
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable("id") Long userId) {
		userService.deleteUser(userId);
	}

	@Override
	@GetMapping
	public List<ResponseUserDto> getAllUsers() {
		return userService.getAllUsers();
	}

	@Override
	@DeleteMapping
	public void deleteAllUsers() {
		userService.deleteAllUsers();
	}
}