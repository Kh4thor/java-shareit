package ru.practicum.shareit.item.mvc.controller.controller;

import java.util.List;

import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ResponseItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;

public interface ItemControllerApp {

	ResponseItemDto createItem(Long ownerId, CreateItemDto itemDto);

	ResponseItemDto updateItem(Long ownerId, UpdateItemDto itemDto, Long itemId);

	ResponseItemDto getItem(Long itemId);

	List<ResponseItemDto> deleteAllItems();

	List<ResponseItemDto> getItemsOfOwner(Long ownerId);

	List<ResponseItemDto> searchItemByText(String text, Long ownerId);

}