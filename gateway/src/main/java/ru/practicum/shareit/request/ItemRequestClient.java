package ru.practicum.shareit.request;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.GetItemRequestDto;
import ru.practicum.shareit.request.dto.ResponseItemRequestDto;

import java.util.List;

@FeignClient(name = "shareit-server", url = "http://server:9090")
public interface ItemRequestClient {

    @PostMapping("/requests")
    ResponseItemRequestDto createItemRequest(
            @RequestHeader("X-Sharer-User-Id") Long ownerId,
            @RequestBody CreateItemRequestDto createItemRequestDto);

    @PostMapping("/requests/{id}/get")  // Изменен метод на POST для передачи тела
    ResponseItemRequestDto getItemRequestWithBody(
            @PathVariable("id") Long itemRequestId,
            @RequestBody GetItemRequestDto getItemRequestDto);

    @GetMapping("/requests")
    List<ResponseItemRequestDto> getAllItemRequestsOfOwner(
            @RequestHeader("X-Sharer-User-Id") Long requestorId);
}