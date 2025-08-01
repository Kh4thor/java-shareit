package ru.practicum.shareit.item.mvc.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ResponseItemDto;

@Slf4j
@RestController("/items")
public class ItemController {

	private final ItemService itemService;

	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@PostMapping
	public ResponseItemDto createItem(@RequestHeader("X-Sharer-User-Id") Long ownerId, CreateItemDto itemDto) {
		return itemService.createItem(itemDto, ownerId);
	}

	@GetMapping("/{id}")
	public ResponseItemDto getItem(@RequestParam("id") Long itemId) {
		return itemService.getItem(itemId);
	}


	@DeleteMapping
	public List<ResponseItemDto> deleteAllItems() {
		return itemService.deleteAllItems();
	}

//	@PatchMapping("/{itemId}")
//	public ResponseItemDto updateItem(@RequestHeader("X-Sharer-User-Id") Long userId, CreateItemDto itemDto) {
//		log.info("Начато обновление предмета. Получен объект:" + itemDto + " и id-пользователя=" + userId);
//		return itemService.updateItem(itemDto, userId);
//	}

//	@GetMapping("/search?text={text}")
//	public List<ResponseItemDto> searchItemByText(@PathVariable("text") String text) {
//		log.info("Начат поиск предмета. Получена строка:" + text);
//		return itemService.searchItemByText(text);
//	}

}
