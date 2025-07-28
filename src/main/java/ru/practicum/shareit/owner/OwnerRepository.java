package ru.practicum.shareit.owner;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import ru.practicum.shareit.user.repository.UserRepository;

public class OwnerRepository extends UserRepository {

	public OwnerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
		super(namedParameterJdbcTemplate, jdbcTemplate);
	}
}
