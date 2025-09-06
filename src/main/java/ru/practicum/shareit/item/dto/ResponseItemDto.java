package ru.practicum.shareit.item.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.booking.mvc.model.Booking;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseItemDto {
	private Long id;
	private String name;
	private Long ownerId;
	private Boolean available;
	private Long itemRequestId;
	private String description;
	private Booking nextBooking;
	private Booking lastBooking;
	private List<ResponseCommentDto> comments;
}