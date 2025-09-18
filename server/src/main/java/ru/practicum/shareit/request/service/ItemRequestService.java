package ru.practicum.shareit.request.service;

import java.util.List;

import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.GetItemRequestDto;
import ru.practicum.shareit.request.dto.ResponseItemRequestDto;

public interface ItemRequestService {
    ResponseItemRequestDto createItemRequest(CreateItemRequestDto createRequestDto);

    ResponseItemRequestDto getItemRequest(GetItemRequestDto getItemRequestDto);

	List<ResponseItemRequestDto> getOwnItemRequestsOfUser(Long requestorId);

	List<ResponseItemRequestDto> getItemRequestsCreatedByOtherUsers(Long requestorId);
}
