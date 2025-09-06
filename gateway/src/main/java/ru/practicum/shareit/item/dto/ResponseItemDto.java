package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

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
    private BookingInfo nextBooking;
    private BookingInfo lastBooking;
    private List<ResponseCommentDto> comments;

    @Getter
    @Setter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookingInfo {
        private Long id;
        private Long bookerId;
    }
}