package ru.practicum.shareit.item.exception;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.mvc.controller.ItemRepository;

@Slf4j
@Component
public class ItemException {

	private final ItemRepository itemRepository;

	public ItemException(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	public void checkItemNotFoundException(Long itemId, String errorMessage) {
		if (!itemRepository.isItemExists(itemId)) {
			RuntimeException exception = new ItemNotFoundException(itemId, errorMessage);
			log.warn(errorMessage + " " + exception.getMessage());
			throw exception;
		}
	}

	public void checkItemAlreadyExistsException(Long itemId, String errorMessage) {
		if (itemRepository.isItemExists(itemId)) {
			RuntimeException exception = new ItemAlreadyExistsException(itemId, errorMessage);
			log.warn(errorMessage + " " + exception.getMessage());
			throw exception;
		}
	}

	public void checkItemAlreadyBelongsToTheOwnerException(Long itemId, Long ownerId, String errorMessage) {
		if (itemRepository.isItemBelongsOwner(itemId, ownerId) == true) {
			RuntimeException exception = new ItemAlreadyBelongsToTheOwnerException(itemId, ownerId, errorMessage);
			log.warn(errorMessage + " " + exception.getMessage());
			throw exception;
		}
	}

	public void checkItemDoesNotBelongToTheOwnerException(Long itemId, Long ownerId, String errorMessage) {
		if (itemRepository.isItemBelongsOwner(itemId, ownerId) == false) {
			RuntimeException exception = new ItemDoesNotBelongToTheOwnerException(itemId, ownerId, errorMessage);
			log.warn(errorMessage + " " + exception.getMessage());
			throw exception;
		}
	}
}
