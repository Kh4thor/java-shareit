//package ru.practicum.shareit.item.mvc.controller;
//
//import java.util.List;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PatchMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RestController;
//
//import lombok.extern.slf4j.Slf4j;
//import ru.practicum.shareit.item.dto.CreateItemDto;
//import ru.practicum.shareit.item.mvc.model.Item;
//
//@Slf4j
//@RestController("/items")
//public class ItemController {
//
//	private final ItemService itemService;
//
//	public ItemController(ItemService itemService) {
//		this.itemService = itemService;
//	}
//
//	@PostMapping
//	public Item createItem(@RequestHeader("X-Sharer-User-Id") Long userId, CreateItemDto itemDto) {
//		log.info("Начато создание предмета. Получен объект:" + itemDto + " и id-пользователя=" + userId);
//		return itemService.createItem(itemDto, userId);
//	}
//
//	@PatchMapping("/{itemId}")
//	public Item updateItem(@RequestHeader("X-Sharer-User-Id") Long userId, CreateItemDto itemDto) {
//		log.info("Начато обновление предмета. Получен объект:" + itemDto + " и id-пользователя=" + userId);
//		return itemService.updateItem(itemDto, userId);
//	}
//
//	@GetMapping("/search?text={text}")
//	public List<Item> searchItemByText(@PathVariable("text") String text) {
//		log.info("Начат поиск предмета. Получена строка:" + text);
//		return itemService.searchItemByText(text);
//	}
//
//}
