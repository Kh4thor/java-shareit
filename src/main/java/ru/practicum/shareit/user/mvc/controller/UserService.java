package ru.practicum.shareit.user.mvc.controller;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.mvc.controller.ItemRepository;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.ResponseUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.exception.UserException;

@Slf4j
@Service
public class UserService {

	private final UserException userException;
	private final UserRepository userRepository;
	private final ItemRepository itemRepository;

	public UserService(UserException userValidator, UserRepository userRepository, ItemRepository itemRepository) {
		this.userException = userValidator;
		this.userRepository = userRepository;
		this.itemRepository = itemRepository;
	}

	public ResponseUserDto createUser(@NotNull CreateUserDto сreateUserDto) {
		String errorMessage = "Невозможно создать пользователя.";
		userException.checkEmailAlreadyExistsException(сreateUserDto.getEmail(), errorMessage);

		log.info("Начато создание пользователя. Получен объект: " + сreateUserDto);
		ResponseUserDto createdUser = userRepository.createUser(сreateUserDto);
		log.info("Создан пользователь: " + createdUser);

		return createdUser;
	}
	
	public ResponseUserDto updateUser(UpdateUserDto updateUserDto) {
		Long userId = updateUserDto.getUserId();
		String email = updateUserDto.getEmail();

		String errorMessage = "Невозможно обновить пользователя.";
		userException.checkUserNotFoundException(userId, errorMessage);
		userException.checkEmailAlreadyExistsException(email, errorMessage);

		return userRepository.updateUser(updateUserDto);
	}

	public ResponseUserDto getUser(Long userId) {
		String errorMessage = "Невозможно получить пользователя.";
		userException.checkUserNotFoundException(userId, errorMessage);

		log.info("Начато получение пользователя. Получен id: " + userId);
		ResponseUserDto getedUser = userRepository.getUser(userId);
		log.info("Получен пользователь: " + getedUser);
		return getedUser;
	}

	public ResponseUserDto deleteUser(@NotNull Long userId) {
		String errorMessage = "Невозможно удалить пользователя.";
		userException.checkUserNotFoundException(userId, errorMessage);

		log.info("Начато удаление пользователя. Получен id: " + userId);
		ResponseUserDto deletedUser = userRepository.deleteUser(userId);
		log.info("Удален пользователь: " + deletedUser);

		Long ownerId = deletedUser.getId();
		deleteOwner(ownerId);
		
		return deletedUser;
	}

	public List<ResponseUserDto> getAllUsers() {
		log.info("Начато получение всех пользователей.");
		List<ResponseUserDto> allUsersList = userRepository.getAllUsers();
		log.info("Получен список всех пользователей: " + allUsersList);
		return allUsersList;
	}

	public List<ResponseUserDto> deleteAllUsers() {
		log.info("Начато удаление всех пользователей.");
		List<ResponseUserDto> listOfUsersToDelete = userRepository.deleteAllUsers();
		log.info("Получен список удаленных пользователей: " + listOfUsersToDelete);
		return listOfUsersToDelete;
	}

	private void deleteOwner(Long ownerId) {
		if (itemRepository.isOwnerExists(ownerId)) {
			log.info("Начато удаление владельца. Получен id: " + ownerId);
			if (itemRepository.deleteOwner(ownerId)) {
				log.info("Удален владелец id= " + ownerId);
			} else {
				log.warn("Неудачная попытка удалить владельца id= " + ownerId);
			}
		}
	}
}