package ru.practicum.shareit.item.utills;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.dto.ResponseItemDto;

@Slf4j
public class ResponseItemDtoMapper implements RowMapper<ResponseItemDto> {

	@Override
	public ResponseItemDto mapRow(ResultSet rs, int rowNum) throws SQLException, IllegalArgumentException {
		
		return	ResponseItemDto.builder()
				.id(rs.getLong("id"))
				.name(rs.getString("name"))
				.description(rs.getString("description"))
				.available(rs.getBoolean("available"))
				.ownerId(rs.getLong("owner_id"))
				.build();
	}
}