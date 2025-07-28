//package ru.practicum.shareit.item.controller;
//
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import lombok.extern.slf4j.Slf4j;
//import ru.practicum.shareit.item.dto.ItemDto;
//import ru.practicum.shareit.item.model.Item;
//import ru.practicum.shareit.item.service.ItemService;
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
//	public Item createItem(ItemDto itemDto) {
//		log.info("Начато создание предмета. Получен объект:" + itemDto);
//		return itemService.createItem(itemDto);
//	}
//}
