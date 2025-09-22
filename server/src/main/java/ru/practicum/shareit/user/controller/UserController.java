package ru.practicum.shareit.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.ResponseUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.service.UserService;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

	public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseUserDto createUser(@RequestBody CreateUserDto createUserDto) {
        return userService.createUser(createUserDto);
    }

    @PatchMapping("/{id}")
    public ResponseUserDto updateUser(@RequestBody UpdateUserDto updateUserDto, @PathVariable("id") Long userId) {
        updateUserDto.setUserId(userId);
        return userService.updateUser(updateUserDto);
    }

    @GetMapping("/{id}")
    public ResponseUserDto getUser(@PathVariable("id") Long userId) {
        return userService.getUser(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping
    public List<ResponseUserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping
    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }
}