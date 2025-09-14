package ru.practicum.shareit.item.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class ItemDto {

	private Long id;
	private String name;
}
