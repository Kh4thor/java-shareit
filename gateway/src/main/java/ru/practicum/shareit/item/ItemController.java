package ru.practicum.shareit.item;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.dto.*;

@Slf4j
@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> createItem(
            @RequestHeader("X-Sharer-User-Id") Long ownerId,
            @RequestBody CreateItemDto itemDto) {
        itemDto.setOwnerId(ownerId);
        return itemClient.createItem(ownerId, itemDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateItem(
            @RequestHeader("X-Sharer-User-Id") Long ownerId,
            @RequestBody UpdateItemDto itemDto,
            @PathVariable("id") Long itemId) {
        itemDto.setOwnerId(ownerId);
        itemDto.setItemId(itemId);
        return itemClient.updateItem(ownerId, itemDto, itemId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItem(@PathVariable("id") Long itemId) {
        return itemClient.getItemWithComments(itemId);
    }

    @DeleteMapping
    public void deleteAllItems() {
        itemClient.deleteAllItems();
    }

    @GetMapping
    public ResponseEntity<Object> getItemsOfOwner(@RequestHeader("X-Sharer-User-Id") Long ownerId) {
        return itemClient.getItemsOfOwner(ownerId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItemByText(
                                                    @RequestParam("text") String text,
                                                    @RequestHeader("X-Sharer-User-Id") Long ownerId) {
        return itemClient.searchItemByText(text, ownerId);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Object> createComment(
            @RequestHeader("X-Sharer-User-Id") @NotNull @Positive Long commentatorId,
            @PathVariable("id") @NotNull @Positive Long itemId,
            @RequestBody CreateCommentDto createCommentDto) {
        return itemClient.createComment(commentatorId, itemId, createCommentDto);
    }
}