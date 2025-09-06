package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ParamsDto {

	private Long userId;
	private Long bookingId;
	private Boolean approve;
	private String state;

}
