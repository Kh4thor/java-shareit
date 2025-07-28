package ru.practicum.shareit.user.controller;

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
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

	private final UserRepository userRepository;


	public UserController(UserService userService, UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostMapping
	public User createUser(@Valid @RequestBody UserDto userDto) {
		return userRepository.createUser(userDto);
	}
	
	@PutMapping
	public User updateUser(@Valid @RequestBody User user) {
		return userRepository.updateUser(user);
	}
	
	@GetMapping("/{id}")
	public User getUser(@PathVariable("id") Long userId) {
		return userRepository.getUser(userId);
	}

	@DeleteMapping("/{id}")
	public User deleteUser(@PathVariable("id") Long userId) {
		return userRepository.deleteUser(userId);
	}

	@GetMapping
	public List<User> getAllUsers() {
		return userRepository.getAllUsers();
	}

	@DeleteMapping
	public List<User> deleteAllUsers() {
		return userRepository.deleteAllUsers();
	}

}
