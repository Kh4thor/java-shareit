package ru.practicum.shareit.item.dto;

import java.util.List;

import lombok.Setter;
import lombok.Getter;
import lombok.Builder;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.model.Booking;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
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