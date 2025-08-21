package ru.practicum.shareit.item.mvc.controller.service;

import java.util.List;

import ru.practicum.shareit.item.dto.CreateCommentDto;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.FindItemDto;
import ru.practicum.shareit.item.dto.ResponseCommentDto;
import ru.practicum.shareit.item.dto.ResponseItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;

public interface ItemServiceApp {

	ResponseItemDto createItem(CreateItemDto createItemDto);

	ResponseItemDto updateItem(UpdateItemDto updateItemDto);

	ResponseItemDto getItem(Long itemId);

	void deleteItem(Long itemId);

	void deleteAllItems();

	List<ResponseItemDto> getItemsOfOwner(Long userId);

	List<ResponseItemDto> searchItemByText(FindItemDto findItemDto);

	ResponseCommentDto createComment(CreateCommentDto createCommentDto);
}