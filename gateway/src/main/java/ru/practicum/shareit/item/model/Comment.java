package ru.practicum.shareit.item.model;

import lombok.*;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Comment {

    private Long id;

    private User commentator;

    private Item item;

    private String text;

    private LocalDateTime created;
}