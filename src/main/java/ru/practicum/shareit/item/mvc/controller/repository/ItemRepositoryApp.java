package ru.practicum.shareit.item.mvc.controller.repository;

import java.util.List;

import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.FindItemDto;
import ru.practicum.shareit.item.dto.ResponseItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;

public interface ItemRepositoryApp {

	ResponseItemDto createItem(CreateItemDto createItemDto);

	ResponseItemDto updateItem(UpdateItemDto updateItemDto);

	ResponseItemDto getItem(Long itemId);

	Boolean isItemExists(Long itemId);

	Boolean setOwnerToItem(Long itemId, Long ownerId);

	Boolean isOwnerExists(Long ownerId);

	Boolean deleteOwner(Long ownerId);

	ResponseItemDto deleteItem(Long itemId);

	Boolean isItemBelongsOwner(Long itemId, Long ownerId);

	List<ResponseItemDto> getAllItems();

	List<ResponseItemDto> deleteAllItems();

	List<ResponseItemDto> getItemsOfOwner(Long ownerId);

	List<ResponseItemDto> searchItemByText(FindItemDto findItemDto);
}