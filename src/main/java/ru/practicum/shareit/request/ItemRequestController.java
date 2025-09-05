package ru.practicum.shareit.request;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ResponseItemRequestDto;

@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {
	private final ItemRequestService itemRequestService;
	public ItemRequestController(ItemRequestService itemRequestService) {
		this.itemRequestService = itemRequestService;
	}

	@PostMapping
	public ResponseItemRequestDto createItemRequest(
			@RequestHeader("X-Sharer-User-Id") Long ownerId,
			@RequestBody CreateItemRequestDto crateItemRequestDto) {
		crateItemRequestDto.setOwnerId(ownerId);
		return itemRequestService.createItemRequest(crateItemRequestDto);
	}
}
