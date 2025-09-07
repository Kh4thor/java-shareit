package ru.practicum.shareit.booking.mvc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
