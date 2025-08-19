package ru.practicum.shareit.booking.mvc.controller.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ApproveDto {

	private Long ownerId;
	private Long bookingId;
	private Boolean approve;

}
