package ru.practicum.shareit.item;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.shareit.item.dto.CreateCommentDto;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ResponseCommentDto;
import ru.practicum.shareit.item.dto.ResponseItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;

import java.util.List;

@FeignClient(name = "item-client", url = "${shareit.server.url}")
public interface ItemClient {

    @PostMapping("/items")
    ResponseItemDto createItem(
            @RequestHeader("X-Sharer-User-Id") Long ownerId,
            @RequestBody CreateItemDto itemDto);

    @PatchMapping("/items/{id}")
    ResponseItemDto updateItem(
            @RequestHeader("X-Sharer-User-Id") Long ownerId,
            @RequestBody UpdateItemDto itemDto,
            @PathVariable("id") Long itemId);

    @GetMapping("/items/{id}")
    ResponseItemDto getItem(@PathVariable("id") Long itemId);

    @DeleteMapping("/items")
    void deleteAllItems();

    @GetMapping("/items")
    List<ResponseItemDto> getItemsOfOwner(@RequestHeader("X-Sharer-User-Id") Long ownerId);

    @GetMapping("/items/search")
    List<ResponseItemDto> searchItemByText(
            @RequestParam String text,
            @RequestHeader("X-Sharer-User-Id") Long ownerId);

    @PostMapping("/items/{id}/comment")
    ResponseCommentDto createComment(
            @RequestHeader("X-Sharer-User-Id") Long commentatorId,
            @PathVariable("id") Long itemId,
            @RequestBody CreateCommentDto createCommentDto);
}