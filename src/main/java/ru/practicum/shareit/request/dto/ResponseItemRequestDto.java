package ru.practicum.shareit.request.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.item.dto.ResponseItemDto;

@Getter
@Setter
@Builder
@ToString
public class ResponseItemRequestDto {

	private Long id;
	private Long requestorId;
	private String description;
	private LocalDateTime created;
	private List<ResponseItemDto> items;
}