package ru.practicum.shareit.item.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.practicum.shareit.item.dto.CreateCommentDto;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.FindItemDto;
import ru.practicum.shareit.item.dto.ResponseCommentDto;
import ru.practicum.shareit.item.dto.ResponseItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.service.ItemService;

@RestController
@RequestMapping("/items")
public class ItemController {

	private final ItemService itemService;

	public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseItemDto createItem(
            @RequestHeader("X-Sharer-User-Id") Long ownerId,
			@RequestBody CreateItemDto itemDto) {
        itemDto.setOwnerId(ownerId);
        return itemService.createItem(itemDto);
    }

    @PatchMapping("/{id}")
    public ResponseItemDto updateItem(
            @RequestHeader("X-Sharer-User-Id") Long ownerId,
            @RequestBody UpdateItemDto itemDto,
            @PathVariable("id") Long itemId) {
        itemDto.setOwnerId(ownerId);
        itemDto.setItemId(itemId);
        return itemService.updateItem(itemDto);
    }

    @GetMapping("/{id}")
    public ResponseItemDto getItem(@PathVariable("id") Long itemId) {
        return itemService.getItemWithComments(itemId);
    }

    @DeleteMapping
    public void deleteAllItems() {
        itemService.deleteAllItems();
    }

    @GetMapping
    public List<ResponseItemDto> getItemsOfOwner(@RequestHeader("X-Sharer-User-Id") Long ownerId) {
        return itemService.getItemsOfOwner(ownerId);
    }

    @GetMapping("/search")
    public List<ResponseItemDto> searchItemByText(
            @RequestParam("text") String text,
            @RequestHeader("X-Sharer-User-Id") Long ownerId) {
        FindItemDto findItemDto = FindItemDto.builder()
                .text(text)
                .ownerId(ownerId)
                .build();
        return itemService.searchItemByText(findItemDto);
    }

    @PostMapping("/{id}/comment")
    public ResponseCommentDto createComment(
            @RequestHeader("X-Sharer-User-Id") Long commentatorId,
            @PathVariable("id") Long itemId,
            @RequestBody CreateCommentDto createCommentDto) {

        createCommentDto.setCommentatorId(commentatorId);
        createCommentDto.setItemId(itemId);

        return itemService.createComment(createCommentDto);
    }
}