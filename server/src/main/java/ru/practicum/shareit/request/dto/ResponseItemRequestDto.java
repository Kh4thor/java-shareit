package ru.practicum.shareit.request.dto;

import lombok.*;
import ru.practicum.shareit.item.dto.ResponseItemDto;

import java.time.LocalDateTime;
import java.util.List;

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