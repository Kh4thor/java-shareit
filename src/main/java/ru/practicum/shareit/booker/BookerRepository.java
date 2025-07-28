package ru.practicum.shareit.booker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import ru.practicum.shareit.user.repository.UserRepository;

public class BookerRepository extends UserRepository{

	public BookerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
		super(namedParameterJdbcTemplate, jdbcTemplate);
	}
}
