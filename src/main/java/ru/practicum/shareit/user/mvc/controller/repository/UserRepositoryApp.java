package ru.practicum.shareit.user.mvc.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.practicum.shareit.user.mvc.model.User;

public interface UserRepositoryApp extends JpaRepository<User, Long> {

}