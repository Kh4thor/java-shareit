package ru.practicum.shareit.item.mvc.controller.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.FindItemDto;
import ru.practicum.shareit.item.dto.ResponseItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.exception.ItemException;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.mvc.controller.repository.ItemRepositoryApp;
import ru.practicum.shareit.item.mvc.controller.service.ItemServiceApp;
import ru.practicum.shareit.item.mvc.model.Item;
import ru.practicum.shareit.item.utills.ItemMapper;
import ru.practicum.shareit.user.exception.UserException;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.mvc.controller.repository.UserRepositoryApp;
import ru.practicum.shareit.user.mvc.model.User;

@Slf4j
@Service
public class ItemService implements ItemServiceApp {

	private final ItemRepositoryApp itemRepository;
	private final UserRepositoryApp userRepository;
	private final UserException userException;
	private final ItemException itemException;

	public ItemService(ItemRepositoryApp itemRepositry, UserException userException, ItemException itemException, UserRepositoryApp userRepository) {
		this.userRepository = userRepository;
		this.itemException = itemException;
		this.userException = userException;
		this.itemRepository = itemRepositry;
	}

	@Override
	@Transactional
	public ResponseItemDto createItem(@NotNull CreateItemDto createItemDto) {
		Long ownerId = createItemDto.getOwnerId();

		String errorMessage = "Невозможно создать предмет.";
		userException.checkUserNotFoundException(ownerId, errorMessage);

		log.info("Начато преобразование (CreateItemDto)createItemDto в объект класса Item. Получен объект: " + createItemDto);
		Item createItem = createItemDtoToItem(createItemDto, errorMessage);
		log.info("(CreateItemDto)createItemDto преобразован в объект класса User: " + createItem);

		log.info("Начато создание предмета. Получен объект:" + createItem);
		Item responseItem = itemRepository.save(createItem);
		log.info("Создан предмет: " + responseItem);

		log.info("Начато преобразование ItemCreateDto в объект ResponseItemDt. Получен объект:" + responseItem);
		ResponseItemDto responseItemDto = ItemMapper.itemToResponseItemDto(responseItem);
		log.info("Закончено преобразование ItemCreateDto в объект ResponseItemDt. Получен объект:" + responseItemDto);

		return responseItemDto;
	}

	@Override
	@Transactional
	public ResponseItemDto updateItem(UpdateItemDto updateItemDto) {
//		Long ownerId = updateItemDto.getOwnerId();
//		Long itemId = updateItemDto.getItemId();

		String errorMessage = "Невозможно обновить предмет";
//		itemException.checkItemNotFoundException(itemId, errorMessage);
//		userException.checkUserNotFoundException(ownerId, errorMessage);
//		itemException.checkItemDoesNotBelongToTheOwnerException(itemId, ownerId, errorMessage);

		log.info("Начато преобразование (UpdateItemDto)updateItemDto в объект класса User. Получен объект: " + updateItemDto);
		Item updateItem = updateItemDtoToItem(updateItemDto, errorMessage);
		log.info("updateUserDto преобразован в объект класса User: " + updateItem);

		log.info("Начато обновление предмета. Получен объект:" + updateItem);
		Item responseItem = itemRepository.save(updateItem);
		log.info("Обновлен предмет " + responseItem);

		log.info("Начато преобразование ItemCreateDto в объект ResponseItemDto. Получен объект:" + responseItem);
		ResponseItemDto responseItemDto = ItemMapper.itemToResponseItemDto(responseItem);
		log.info("Закончено преобразование ItemCreateDto в объект ResponseItemDt. Получен объект:" + responseItemDto);

		return responseItemDto;
	}

	@Override
	public ResponseItemDto getItem(Long itemId) {
		String errorMessage = "Невозможно вызвать объект";

		log.info("Начат вызов предмета. Получен id:" + itemId);
		Item responseItem = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId, errorMessage));
		log.info("Вызван предмет:" + itemId);

		log.info("Начато преобразование ItemCreateDto в объект ResponseItemDt. Получен объект:" + responseItem);
		ResponseItemDto responseItemDto = ItemMapper.itemToResponseItemDto(responseItem);
		log.info("Закончено преобразование ItemCreateDto в объект ResponseItemDt. Получен объект:" + responseItemDto);

		return responseItemDto;
	}

	@Override
	@Transactional
	public void deleteItem(Long itemId) {
		String errorMessage = "Невозможно удалить объект";
		itemException.checkItemNotFoundException(itemId, errorMessage);

		log.info("Начато удаление предмета. Получен id=" + itemId);
		itemRepository.deleteById(itemId);
		log.info("Удален предмет id=" + itemId);
	}

	@Override
	public void deleteAllItems() {
		log.info("Начато удаление всех предметов.");
		itemRepository.deleteAll();
		log.info("Все предметы удалены.");
	}

	@Override
	public List<ResponseItemDto> getItemsOfOwner(Long userId) {
		String errorMessage = "Невозможно получить список предметов пользователя.";
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId, errorMessage));
		
		log.info("Начат процесс получения списка предметов пользователя. Получен id-пользователя=" + userId);
		List<Item> responseItemsList = user.getItems();
		log.info("Получен список предметов пользователя" + responseItemsList);

		log.info("Начато преобразование списка ItemCreateDto в список объектов ResponseItemDt. Получен объект:" + responseItemsList);
		List<ResponseItemDto> responseItemsListDto = 	responseItemsList
														.stream()
														.map(ItemMapper::itemToResponseItemDto)
														.toList();
		log.info("Закончено преобразование ItemCreateDto в объект ResponseItemDt. Получен объект:" + responseItemsListDto);

		return responseItemsListDto;
	}

	@Override
	public List<ResponseItemDto> searchItemByText(FindItemDto findItemDto) {
		Long ownerId = findItemDto.getOwnerId();
		String text = findItemDto.getText();

		String errorMessage = "Невозможно начать поиск предмета владельца по строке поиска";
		userException.checkUserNotFoundException(ownerId, errorMessage);

		if (findItemDto.getText().isBlank()) {
			return new ArrayList<ResponseItemDto>();
		}

		log.info("Начат поиск предмета. Получен id-владельца: " + ownerId + " и строка поиска:" + text);
		List<Item> responseItemsList = itemRepository.searchItemByText(ownerId, text);

		log.info("Начато преобразование списка ItemCreateDto в список объектов ResponseItemDt. Получен объект:" + responseItemsList);
		List<ResponseItemDto> responseItemsListDto = 	responseItemsList
														.stream()
														.map(ItemMapper::itemToResponseItemDto)
														.toList();
		log.info("Закончено преобразование ItemCreateDto в объект ResponseItemDt. Получен объект:" + responseItemsListDto);
		return responseItemsListDto;
	}

	private Item createItemDtoToItem(CreateItemDto createItemDto, String errorMessage) {
		Long ownerId = createItemDto.getOwnerId();
		User owner = userRepository.findById(ownerId).orElseThrow(() -> new UserNotFoundException(ownerId, errorMessage));
		Item item = ItemMapper.createItemDtoToItem(createItemDto);
		item.setOwner(owner);
		return item;
	}

	private Item updateItemDtoToItem(UpdateItemDto updateItemDto, String errorMessage) {
		Long ownerId = updateItemDto.getOwnerId();
		User owner = userRepository.findById(ownerId).orElseThrow(() -> new UserNotFoundException(ownerId, errorMessage));
		Item item = ItemMapper.updateItemDtoToItem(updateItemDto);
		item.setOwner(owner);
		return item;
	}
}