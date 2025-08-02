package ru.practicum.shareit.user.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ru.practicum.shareit.user.dto.ResponseUserDto;
import ru.practicum.shareit.user.mvc.model.User;

public class ResponseUserDtoMapper implements RowMapper<ResponseUserDto> {

	public static ResponseUserDto toUserDto(User user) {
		return	ResponseUserDto.builder()
				.id(null)
				.name(user.getName())
				.email(user.getEmail())
				.build();
	}

	@Override
	public ResponseUserDto mapRow(ResultSet rs, int rowNum) throws SQLException {

		return	ResponseUserDto.builder()
				.id(rs.getLong("id"))
				.name(rs.getString("name"))
				.email(rs.getString("email"))
				.build();
	}
}
