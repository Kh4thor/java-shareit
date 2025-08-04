package ru.practicum.shareit.item.mvc.controller.service;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.FindItemDto;
import ru.practicum.shareit.item.dto.ResponseItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;

public interface ItemServiceApp {

	ResponseItemDto createItem(CreateItemDto createItemDto);

	ResponseItemDto updateItem(UpdateItemDto updateItemDto);

	ResponseItemDto getItem(Long itemId);

	ResponseItemDto deleteItem(Long itemId);

	List<ResponseItemDto> deleteAllItems();

	List<ResponseItemDto> getItemsOfOwner(Long userId);

	List<ResponseItemDto> searchItemByText(FindItemDto findItemDto);

}