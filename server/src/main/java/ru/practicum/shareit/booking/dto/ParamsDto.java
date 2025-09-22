package ru.practicum.shareit.booking.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class ParamsDto {

	private Long userId;
	private Long bookingId;
	private Boolean approve;
	private String state;
}
