package ru.practicum.shareit.item.mvc.controller.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

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