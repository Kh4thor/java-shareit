package ru.practicum.shareit.request.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.GetItemRequestDto;
import ru.practicum.shareit.request.dto.ResponseItemRequestDto;
import ru.practicum.shareit.request.dto.ResponseItemRequestListDto;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.request.service.ItemRequestServiceImpl;

@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

	public ItemRequestController(ItemRequestServiceImpl itemRequestService, ItemService itemService) {
        this.itemRequestService = itemRequestService;
    }

    @PostMapping
    public ResponseItemRequestDto createItemRequest(
            @RequestHeader("X-Sharer-User-Id") Long ownerId,
            @RequestBody CreateItemRequestDto crateItemRequestDto) {
        crateItemRequestDto.setOwnerId(ownerId);
        return itemRequestService.createItemRequest(crateItemRequestDto);
    }

    @GetMapping("/{id}")
    public ResponseItemRequestDto getItemRequest(
            @RequestHeader("X-Sharer-User-Id") Long ownerId,
            @PathVariable("id") Long itemRequestId) {
        GetItemRequestDto getItemRequestDto =	GetItemRequestDto.builder()
								                .itemRequestId(itemRequestId)
								                .ownerId(ownerId)
								                .build();
        return itemRequestService.getItemRequest(getItemRequestDto);
    }

    @GetMapping
	public List<ResponseItemRequestListDto> getUserRequests(
            @RequestHeader("X-Sharer-User-Id") Long requestorId) {
		return itemRequestService.getItemsByRequestorId(requestorId);
    }

	@GetMapping("/all")
	public List<ResponseItemRequestListDto> getItemsOfUsersExcludingRequestor(
    @RequestHeader("X-Sharer-User-Id") Long requestorId) {
		return itemRequestService.getItemsOfUsersExcludingRequestorById(requestorId);
	}
}