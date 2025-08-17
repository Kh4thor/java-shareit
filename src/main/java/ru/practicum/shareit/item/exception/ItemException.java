package ru.practicum.shareit.item.exception;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.mvc.controller.repository.ItemRepositoryApp;
import ru.practicum.shareit.user.mvc.controller.repository.UserRepositoryApp;

@Slf4j
@Component
public class ItemException {

	private final UserRepositoryApp userRepositoryApp;

	private final ItemRepositoryApp itemRepository;

	public ItemException(ItemRepositoryApp itemRepository, UserRepositoryApp userRepositoryApp) {
		this.itemRepository = itemRepository;
		this.userRepositoryApp = userRepositoryApp;
	}

	public void checkItemNotFoundException(Long itemId, String errorMessage) {
		if (itemRepository.isItemExists(itemId) == false) {
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

//	public void checkItemAlreadyBelongsToTheOwnerException(Long itemId, Long ownerId, String errorMessage) {
//		if (itemRepository.isItemBelongsOwner(itemId, ownerId)) {
//			RuntimeException exception = new ItemAlreadyBelongsToTheOwnerException(itemId, ownerId, errorMessage);
//			log.warn(errorMessage + " " + exception.getMessage());
//			throw exception;
//		}
//	}

//	public void checkItemDoesNotBelongToTheOwnerException(Long itemId, Long ownerId, String errorMessage) {
//		if (!itemRepository.isItemBelongsOwner(itemId, ownerId)) {
//			RuntimeException exception = new ItemDoesNotBelongToTheOwnerException(itemId, ownerId, errorMessage);
//			log.warn(errorMessage + " " + exception.getMessage());
//			throw exception;
//		}
//	}

	public void checkOwnerNotFoundException(Long ownerId, String errorMessage) {
		if (!userRepositoryApp.isUserExists(ownerId)) {
			RuntimeException exception = new OwnerNotFoundException(ownerId, errorMessage);
			log.warn(errorMessage + " " + exception.getMessage());
			throw exception;
		}
	}

//	public void checkOwnerOfItemNotFoundException(Long itemId, String errorMessage) {
//		if (!itemRepository.isItemHasOwner(itemId)) {
//			RuntimeException exception = new OwnerNotFoundException(itemId, errorMessage);
//			log.warn(errorMessage + " " + exception.getMessage());
//			throw exception;
//		}
//	}
}
