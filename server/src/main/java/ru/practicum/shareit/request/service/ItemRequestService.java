package ru.practicum.shareit.request.service;

import java.util.List;

import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.GetItemRequestDto;
import ru.practicum.shareit.request.dto.ResponseItemRequestDto;
import ru.practicum.shareit.request.dto.ResponseItemRequestListDto;

public interface ItemRequestService {
    ResponseItemRequestDto createItemRequest(CreateItemRequestDto createRequestDto);

    ResponseItemRequestDto getItemRequest(GetItemRequestDto getItemRequestDto);

	List<ResponseItemRequestListDto> getItemsByRequestorId(Long requestorId);

	List<ResponseItemRequestListDto> getItemsOfUsersExcludingRequestorById(Long requestorId);
}
