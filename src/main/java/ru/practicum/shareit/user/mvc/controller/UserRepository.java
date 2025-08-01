package ru.practicum.shareit.user.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.ResponseUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.utils.ResponseUserDtoMapper;

@Component
public class UserRepository {


	protected final JdbcTemplate jdbcTemplate;
	protected final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public UserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public ResponseUserDto createUser(CreateUserDto userDto) {

		Map<String, Object> createUserParams = new HashMap<>();
		createUserParams.put("name", userDto.getName());
		createUserParams.put("email", userDto.getEmail());

		String createUserSql = ""
				+ "INSERT INTO users (name, email) "
				+ "VALUES (:name, :email)";

		namedParameterJdbcTemplate.update(createUserSql, createUserParams);

		String lastAddedUserIdSql = ""
				+ "SELECT MAX (id) "
				+ "FROM users";

		Long lastAddedUserIdId = jdbcTemplate.queryForObject(lastAddedUserIdSql, Long.class);
		return getUser(lastAddedUserIdId);
	}
	
	public ResponseUserDto updateEmailOfUser(UpdateUserDto user, Long userId) {
		String updateUSerSql = ""
				+ "UPDATE users "
				+ "SET "
				+ "email = :email "
				+ "WHERE id = :id";
		Map<String, Object> updateUserParams = new HashMap<>();
		
		updateUserParams.put("id", userId);
		updateUserParams.put("email", user.getEmail());
		namedParameterJdbcTemplate.update(updateUSerSql, updateUserParams);
		
		return getUser(userId);
	}
	
	public ResponseUserDto updateNameOfUser(UpdateUserDto user, Long userId) {
		String updateUSerSql = ""
				+ "UPDATE users "
				+ "SET "
				+ "name = :name "
				+ "WHERE id = :id";
		Map<String, Object> updateUserParams = new HashMap<>();

		updateUserParams.put("id", userId);
		updateUserParams.put("name", user.getName());
		namedParameterJdbcTemplate.update(updateUSerSql, updateUserParams);
	
		return getUser(userId);
	}

	public ResponseUserDto updateUser(UpdateUserDto user, Long userId) {
		String updateUSerSql = ""
				+ "UPDATE users "
				+ "SET "
				+ "name = :name, "
					+ "email = :email "
				+ "WHERE id = :id";
	
		Map<String, Object> updateUserParams = new HashMap<>();

		updateUserParams.put("id", userId);
		updateUserParams.put("name", user.getName());
		updateUserParams.put("email", user.getEmail());
		namedParameterJdbcTemplate.update(updateUSerSql, updateUserParams);
	
		return getUser(userId);
	}

	public ResponseUserDto getUser(Long id) {
		String getUserSql = ""
				+ "SELECT "
					+ "u.id,"
					+ "u.name, "
					+ "u.email "
				+ "FROM users AS u "
				+ "WHERE u.id = :id";

		Map<String, Object> getUserParams = new HashMap<>();
		getUserParams.put("id", id);
		getUserParams.put("activity", true);

		return namedParameterJdbcTemplate.queryForObject(getUserSql, getUserParams, new ResponseUserDtoMapper());
	}
	
	public ResponseUserDto deleteUser(Long userId) {
		String removeUserSql = ""
				+ "UPDATE users "
				+ "SET activity = :activity "
				+ "WHERE id = :id";
		Map<String, Object> deleteUserParams = new HashMap<>();
		deleteUserParams.put("id", userId);
		deleteUserParams.put("activity", false);
		namedParameterJdbcTemplate.update(removeUserSql, deleteUserParams);
		return getUser(userId);
	}
	
	public List<ResponseUserDto> getAllUsers() {
		String getAllUsersSql = ""
				+ "SELECT * "
				+ "FROM users "
				+ "WHERE activity = :activity";
		Map<String, Object> getAllUsersParams = new HashMap<>();
		getAllUsersParams.put("activity", true);
		return namedParameterJdbcTemplate.query(getAllUsersSql, getAllUsersParams, new ResponseUserDtoMapper());
	}
	

	public boolean isUserExists (Long id) {
		String isUserExistsSql = ""
				+ "SELECT EXISTS (SELECT 1 "
								+ "FROM users "
								+ "WHERE id = :id AND activity = :activity)";

		Map<String, Object> getUserParams = new HashMap<>();
		getUserParams.put("id", id);
		getUserParams.put("activity", true);

		return namedParameterJdbcTemplate.queryForObject(isUserExistsSql, getUserParams, Boolean.class);
	}
	
	public boolean isEmailAllreadyExists(String email) {
		String isEmailAllreadyExistsSql  = ""
				+ "SELECT EXISTS (SELECT 1 "
								+ "FROM users "
								+ "WHERE email = :email AND activity = :activity)";
		Map<String, Object> isEmailAllreadyExistsParams = new HashMap<>();
		isEmailAllreadyExistsParams.put("email", email);
		isEmailAllreadyExistsParams.put("activity", true);
		return namedParameterJdbcTemplate.queryForObject(isEmailAllreadyExistsSql, isEmailAllreadyExistsParams, Boolean.class);
	}

	public List<ResponseUserDto> deleteAllUsers() {
		String deleteAllUsersSql = ""
				+ "UPDATE users "
				+ "SET activity = :newActivity "
				+ "WHERE activity = :currentActivity";
		Map<String, Object> deleteAllUsersParams = new HashMap<>();

		List<ResponseUserDto> listOfUsersToDelete = getAllUsers();

		deleteAllUsersParams.put("newActivity", false);
		deleteAllUsersParams.put("currentActivity", true);
		namedParameterJdbcTemplate.update(deleteAllUsersSql, deleteAllUsersParams);
		
		return listOfUsersToDelete;
	}
}
