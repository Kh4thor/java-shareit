package ru.practicum.shareit.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.ResponseUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;

import java.util.List;

@FeignClient(name = "shareit-server", url = "http://server:9090")
public interface UserClient {

    @PostMapping("/users")
    ResponseUserDto createUser(@RequestBody CreateUserDto createUserDto);

    @PatchMapping("/users/{id}")
    ResponseUserDto updateUser(@RequestBody UpdateUserDto updateUserDto, @PathVariable("id") Long userId);

    @GetMapping("/users/{id}")
    ResponseUserDto getUser(@PathVariable("id") Long userId);

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable("id") Long userId);

    @GetMapping("/users")
    List<ResponseUserDto> getAllUsers();

    @DeleteMapping("/users")
    void deleteAllUsers();
}