package ru.practicum.shareit.booking.mvc.model.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.booking.utills.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

@Getter
@Setter
@Builder
@ToString
public class ResponseBookingDto {

	private Long id;
	private ItemDto item;
	private UserDto booker;
	private LocalDateTime start;
	private LocalDateTime end;
	private BookingStatus status;
}
