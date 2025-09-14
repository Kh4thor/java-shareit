package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import ru.practicum.shareit.booking.utills.BookingStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateBookingDto {

	private Long bookerId;

	@NotNull
	@Positive
	private Long itemId;

	@NotNull
	@FutureOrPresent
	private LocalDateTime start;

	@NotNull
	@Future
	private LocalDateTime end;

	private BookingStatus bookingStatus;

	@AssertTrue(message = "Дата окончания бронирования не может быть раньше начала бронирования")
	public boolean isEndAfterStart() {
		return end.isAfter(start);
	}
}
