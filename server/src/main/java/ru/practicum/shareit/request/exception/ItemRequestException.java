package ru.practicum.shareit.request.exception;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.request.ItemRequestRepositoryApp;

@Slf4j
@Component
public class ItemRequestException {

	private final ItemRequestRepositoryApp itemRequestRepository;

	public ItemRequestException(ItemRequestRepositoryApp itemRequestRepository) {
			this.itemRequestRepository = itemRequestRepository;
		}

		public void checkItemRequestNotFoundException(Long itemRequestId, String errorMessage) {
			if (!itemRequestRepository.existsById(itemRequestId)) {
				RuntimeException exception = new ItemRequestNotFoundException(itemRequestId, errorMessage);
			log.warn(errorMessage + " " + exception.getMessage());
			throw exception;
		}
	}
}
