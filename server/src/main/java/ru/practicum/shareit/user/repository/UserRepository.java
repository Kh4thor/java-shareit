package ru.practicum.shareit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.practicum.shareit.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select case when exists (select 1 from User u where u.email = :email) then true else false end")
	public Boolean isEmailExists(@Param("email") String email);
}