package ru.practicum.shareit.user.mvc.controller.repository;

import java.util.List;

import ru.practicum.shareit.user.mvc.model.User;

public interface UserRepositoryApp {

	User createUser(User createUserDto);

	User updateUser(User updateUserDto);

	User getUser(Long id);

	User deleteUser(Long userId);

	List<User> getAllUsers();

	Boolean isUserExists(Long id);

	Boolean isEmailExists(String email);

	Boolean isUserOwnerOfEmail(Long user, String email);

	List<User> deleteAllUsers();

}