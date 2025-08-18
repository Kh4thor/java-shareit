package ru.practicum.shareit.booking.mvc.model.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.booking.utills.BookingStatus;
import ru.practicum.shareit.user.mvc.model.User;

@Getter
@Setter
@Builder
@ToString
public class ResponseBookingDto {

//	private Long bookerId;
	private User booker;
	private Long itemId;
	private String itemName;
	private LocalDateTime start;
	private LocalDateTime end;
	private Boolean itemAvailable;
	private BookingStatus status;
}
