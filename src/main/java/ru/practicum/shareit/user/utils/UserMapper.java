package ru.practicum.shareit.user.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ru.practicum.shareit.user.mvc.model.User;

public class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {

		return	User.builder()
				.id(rs.getLong("id"))
				.name(rs.getString("name"))
				.email(rs.getString("email"))
				.build();
	}
}
