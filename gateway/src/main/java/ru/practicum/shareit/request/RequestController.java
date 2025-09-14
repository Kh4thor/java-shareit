package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;

@Slf4j
@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Validated
public class RequestController {
    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> createItemRequest(
            @RequestHeader("X-Sharer-User-Id") Long ownerId,
            @RequestBody CreateItemRequestDto crateItemRequestDto) {
        return requestClient.createItemRequest(ownerId, crateItemRequestDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItemRequest(
            @RequestHeader("X-Sharer-User-Id") Long ownerId,
            @PathVariable("id") Long itemRequestId) {
        return requestClient.getItemRequest(ownerId, itemRequestId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllItemRequestsOfOwner(
            @RequestHeader("X-Sharer-User-Id") Long requestorId) {
        return requestClient.getAllItemRequestsOfOwner(requestorId);
    }
}
