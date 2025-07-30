package ru.practicum.shareit.user.mvc.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mvc.model.User;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;


	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public UserDto createUser(@Valid @RequestBody UserDto userDto) {
		return userService.createUser(userDto);
	}
	
	@PutMapping
	public User updateUser(@Valid @RequestBody User user) {
		return userService.updateUser(user);
	}
	
	@GetMapping("/{id}")
	public User getUser(@PathVariable("id") Long userId) {
		return userService.getUser(userId);
	}

	@DeleteMapping("/{id}")
	public User deleteUser(@PathVariable("id") Long userId) {
		return userService.deleteUser(userId);
	}

	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@DeleteMapping
	public List<User> deleteAllUsers() {
		return userService.deleteAllUsers();
	}

}
