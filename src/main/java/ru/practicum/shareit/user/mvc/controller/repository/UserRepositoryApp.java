package ru.practicum.shareit.user.mvc.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.practicum.shareit.user.mvc.model.User;

public interface UserRepositoryApp extends JpaRepository<User, Long> {

	final String isEmailExistsSql = "" + "SELECT EXISTS (SELECT 1 " + "FROM users " + "WHERE user_email = :email)";

	final String isUserExistsSql = "" + "SELECT EXISTS (SELECT 1 " + "FROM users " + "WHERE user_id = :userId)";

	@Query(value = isEmailExistsSql, nativeQuery = true)
	public Boolean isEmailExists(@Param("email") String email);

	@Query(value = isUserExistsSql, nativeQuery = true)
	public Boolean isUserExists(@Param("userId") Long userId);
}