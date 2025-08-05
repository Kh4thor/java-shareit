package ru.practicum.shareit.item.mvc.controller.controller.impl;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.FindItemDto;
import ru.practicum.shareit.item.dto.ResponseItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.mvc.controller.controller.ItemControllerApp;
import ru.practicum.shareit.item.mvc.controller.service.ItemServiceApp;
import ru.practicum.shareit.item.mvc.model.Item;
import ru.practicum.shareit.item.utills.ItemMapper;

@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController implements ItemControllerApp {

	private final ItemServiceApp itemService;
	private final ItemMapper itemMapper;

	public ItemController(ItemServiceApp itemService, ItemMapper itemMapper) {
		this.itemService = itemService;
		this.itemMapper = itemMapper;
	}

	@Override
	@PostMapping
	public ResponseItemDto createItem(
			@RequestHeader("X-Sharer-User-Id") Long ownerId,
			@Validated @RequestBody CreateItemDto itemDto) {
		itemDto.setOwnerId(ownerId);

		Item responseItem = itemService.createItem(itemDto);

		ResponseItemDto responseItemDto = itemMapper.itemToResponseItemDto(responseItem);

		return responseItemDto;
	}

	@Override
	@PatchMapping("/{id}")
	public ResponseItemDto updateItem(
			@RequestHeader("X-Sharer-User-Id") Long ownerId,
			@Validated @RequestBody UpdateItemDto itemDto,
			@PathVariable("id") Long itemId) {
		itemDto.setOwnerId(ownerId);
		itemDto.setItemId(itemId);

		Item responseItem = itemService.updateItem(itemDto);

		ResponseItemDto responseItemDto = itemMapper.itemToResponseItemDto(responseItem);

		return responseItemDto;
	}

	@Override
	@GetMapping("/{id}")
	public ResponseItemDto getItem(@PathVariable("id") Long itemId) {
		Item responseItem = itemService.getItem(itemId);

		ResponseItemDto responseItemDto = itemMapper.itemToResponseItemDto(responseItem);

		return responseItemDto;

	}

	@Override
	@DeleteMapping
	public List<ResponseItemDto> deleteAllItems() {
		List<Item> responseItemsList = itemService.deleteAllItems();
		
		return	responseItemsList
				.stream()
				.map(itemMapper::itemToResponseItemDto)
				.toList();

	}

	@Override
	@GetMapping
	public List<ResponseItemDto> getItemsOfOwner(@RequestHeader("X-Sharer-User-Id") Long ownerId) {
		List<Item> responseItemsList = itemService.getItemsOfOwner(ownerId);
		
		return	responseItemsList
				.stream()
				.map(itemMapper::itemToResponseItemDto)
				.toList();
	}

	@Override
	@GetMapping("/search")
	public List<ResponseItemDto> searchItemByText(@RequestParam String text, @RequestHeader("X-Sharer-User-Id") Long ownerId) {
		List<Item> responseItemsList =  itemService.searchItemByText(FindItemDto.builder().text(text).ownerId(ownerId).build());
		
		return	responseItemsList
				.stream()
				.map(itemMapper::itemToResponseItemDto)
				.toList();
	}
}