package ru.practicum.shareit.user.mvc.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.practicum.shareit.user.mvc.model.User;

public interface UserRepositoryApp extends JpaRepository<User, Long> {

	public User findByEmail(String email);

	public Boolean isEmailExists(String email);

	public Boolean isUserExists(Long id);
}