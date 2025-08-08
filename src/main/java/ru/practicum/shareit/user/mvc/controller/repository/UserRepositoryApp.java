package ru.practicum.shareit.user.mvc.controller.repository;

import java.util.List;
import java.util.Optional;

import ru.practicum.shareit.user.mvc.model.User;

public interface UserRepositoryApp {

	Optional<User> createUser(User createUserDto);

	Optional<User> updateUser(User updateUser);

	Optional<User> getUser(Long id);

	Optional<User> deleteUser(Long userId);

	List<User> getAllUsers();

	Boolean isUserExists(Long id);

	Boolean isEmailExists(String email);

	Boolean isUserOwnerOfEmail(Long user, String email);

	List<User> deleteAllUsers();
}