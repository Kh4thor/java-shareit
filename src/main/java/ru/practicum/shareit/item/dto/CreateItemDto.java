package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.request.ItemRequest;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class CreateItemDto {

	@NotBlank
	private String name;

	@NotBlank
	private String description;

	@NotNull
	private Boolean available;
	private ItemRequest itemRequest;
}
