package ru.practicum.shareit.request;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;

@Slf4j
@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Valid
public class RequestController {
    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> createItemRequest(
            @RequestHeader("X-Sharer-User-Id") @Positive @NotNull Long ownerId,
            @RequestBody @Valid CreateItemRequestDto crateItemRequestDto) {
        return requestClient.createItemRequest(ownerId, crateItemRequestDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItemRequest(
            @RequestHeader("X-Sharer-User-Id") @Positive @NotNull Long ownerId,
            @PathVariable("id") @Positive @NotNull Long itemRequestId) {
        return requestClient.getItemRequest(ownerId, itemRequestId);
    }

	@GetMapping
	public ResponseEntity<Object> getItemRequestsByRequestorId(
			@RequestHeader("X-Sharer-User-Id") @Positive @NotNull Long requestorId) {
		return requestClient.getItemRequestsByRequestorId(requestorId);
	}

	@GetMapping("/all")
	public ResponseEntity<Object> getItemsOfUsersExcludingRequestor(
			@RequestHeader("X-Sharer-User-Id") @Positive @NotNull Long requestorId) {
		return requestClient.getItemsOfUsersExcludingRequestor(requestorId);
	}

}