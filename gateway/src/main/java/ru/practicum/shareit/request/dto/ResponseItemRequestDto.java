package ru.practicum.shareit.request.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.item.dto.ResponseItemDto;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class ResponseItemRequestDto {

	private Long id;
	private Long requestorId;
	private String description;
	private LocalDateTime created;
	private List<ResponseItemDto> items;
}