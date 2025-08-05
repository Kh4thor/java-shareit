package ru.practicum.shareit.user.mvc.controller.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ru.practicum.shareit.user.mvc.controller.repository.UserRepositoryApp;
import ru.practicum.shareit.user.mvc.model.User;

@Repository
public class UserRepositoryInMemory implements UserRepositoryApp {

	private Map<Long, User> users = new HashMap<>();

	private Long id = 0L;

	private Long generateId() {
		return ++id;
	}

	public User createUser(User createUser) {
		Long userId = generateId();
		createUser.setId(id);
		users.put(id, createUser);
		return getUser(userId);
	}

	@Override
	public User updateUser(User updateUser) {
		Long userId = updateUser.getId();
		User user = getUser(userId);

		String nameCurrentValue = user.getName();
		String nameValueToUpdate = updateUser.getName();

		String emailCurrentValue = user.getEmail();
		String emailValueToUpdate = updateUser.getEmail();

		String name = nameValueToUpdate == null || nameValueToUpdate.isBlank() ? nameCurrentValue : nameValueToUpdate;
		String email = emailValueToUpdate == null || emailValueToUpdate.isBlank() ? emailCurrentValue : emailValueToUpdate;

		updateUser.setName(name);
		updateUser.setEmail(email);

		users.put(userId, updateUser);
		return getUser(userId);
	}

	@Override
	public User getUser(Long userId) {
		return users.get(userId);
	}

	@Override
	public User deleteUser(Long userId) {
		return users.remove(userId);
	}

	@Override
	public List<User> getAllUsers() {
		return users.values().stream().toList();
	}

	@Override
	public Boolean isUserExists(Long userId) {
		return users.containsKey(userId);
	}

	@Override
	public Boolean isEmailExists(String email) {
		return users.values().stream().anyMatch(e -> e.getEmail().equals(email));
	}

	@Override
	public Boolean isUserOwnerOfEmail(Long userId, String email) {
		return users.get(userId).getEmail().equals(email);
	}

	@Override
	public List<User> deleteAllUsers() {
		List<User> deletedUsers = getAllUsers();
		users.clear();
		return deletedUsers;
	}
}
