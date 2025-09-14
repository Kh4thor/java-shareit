package ru.practicum.shareit.item.dto;

import lombok.*;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

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