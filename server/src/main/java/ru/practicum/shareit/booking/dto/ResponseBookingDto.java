package ru.practicum.shareit.booking.dto;

import lombok.*;
import ru.practicum.shareit.booking.utills.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class ResponseBookingDto {

	private Long id;
	private ItemDto item;
	private UserDto booker;
	private LocalDateTime start;
	private LocalDateTime end;
	private BookingStatus status;
}
