package ru.practicum.shareit.item.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class CreateItemDto {

	@NotBlank
	private String name;

	@NotNull
	private String description;

	private Long ownerId;

	private Long requestId;

	@NotNull
	private Boolean available;
}