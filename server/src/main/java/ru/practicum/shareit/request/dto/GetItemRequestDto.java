package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class GetItemRequestDto {
	@Positive
	private Long itemRequestId;
	@Positive
	private Long ownerId;
}
