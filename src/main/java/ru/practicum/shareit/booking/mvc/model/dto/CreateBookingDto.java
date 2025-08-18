package ru.practicum.shareit.booking.mvc.model.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.booking.utills.BookingStatus;

@Getter
@Setter
@Builder
@ToString
public class CreateBookingDto {

	private Long ownerId;

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

	@AssertTrue(message = "Дата окончания не может быть раньше начала")
	public boolean isEndAfterStart() {
		return end.isAfter(start);
	}
}
