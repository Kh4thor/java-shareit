package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GetItemRequestDto {
	@Positive
	private Long itemRequestId;
	@Positive
	private Long ownerId;
}
