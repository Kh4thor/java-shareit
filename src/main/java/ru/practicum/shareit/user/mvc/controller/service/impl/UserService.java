package ru.practicum.shareit.user.mvc.controller.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.mvc.controller.repository.ItemRepositoryApp;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.exception.UserException;
import ru.practicum.shareit.user.mvc.controller.repository.UserRepositoryApp;
import ru.practicum.shareit.user.mvc.controller.service.UserServiceApp;
import ru.practicum.shareit.user.mvc.model.User;
import ru.practicum.shareit.user.utils.UserMapper;

@Slf4j
@Service
public class UserService implements UserServiceApp {

	private final UserException userException;
	private final UserRepositoryApp userRepository;
	private final ItemRepositoryApp itemRepository;

	public UserService(UserException userValidator, UserRepositoryApp userRepository, ItemRepositoryApp itemRepository) {
		this.userException = userValidator;
		this.userRepository = userRepository;
		this.itemRepository = itemRepository;
	}

	@Override
	public User createUser(@NotNull CreateUserDto createUserDto) {
		String errorMessage = "Невозможно создать пользователя.";
		userException.checkEmailAlreadyExistsException(createUserDto.getEmail(), errorMessage);

		log.info("Начато преобразование (CreateUserDto)createUserDto в объект класса User. Получен объект: " + createUserDto);
		User createuser = UserMapper.createUserDtoToUser(createUserDto);
		log.info("createUserDto преобразован в объект класса User: " + createuser);

		log.info("Начато создание пользователя. Получен объект: " + createuser);
		User responseUser = userRepository.createUser(createuser);
		log.info("Создан пользователь: " + responseUser);

		return responseUser;
	}

	@Override
	public User updateUser(UpdateUserDto updateUserDto) {
		Long userId = updateUserDto.getUserId();
		String email = updateUserDto.getEmail();

		String errorMessage = "Невозможно обновить пользователя.";
		userException.checkUserNotFoundException(userId, errorMessage);
		if (userRepository.isUserOwnerOfEmail(userId, email) == false) {
			userException.checkEmailAlreadyExistsException(email, errorMessage);
		}

		log.info("Начато преобразование (UpdateUserDto)updateUserDto в объект класса User. Получен объект: " + updateUserDto);
		User updateUser = UserMapper.updateUserDtoToUser(updateUserDto);
		log.info("updateUserDto преобразован в объект класса User: " + updateUser);

		log.info("Начато обновление пользователя. Получен объект: " + updateUser);
		User responseUser = userRepository.updateUser(updateUser);
		log.info("Обновлен пользователь: " + responseUser);

		return responseUser;
	}

	@Override
	public User getUser(Long userId) {
		String errorMessage = "Невозможно получить пользователя.";
		userException.checkUserNotFoundException(userId, errorMessage);

		log.info("Начато получение пользователя. Получен id: " + userId);
		User responseUser = userRepository.getUser(userId);
		log.info("Получен пользователь: " + responseUser);

		return responseUser;
	}

	@Override
	public User deleteUser(@NotNull Long userId) {
		String errorMessage = "Невозможно удалить пользователя.";
		userException.checkUserNotFoundException(userId, errorMessage);

		log.info("Начато удаление пользователя. Получен id: " + userId);
		User responseUser = userRepository.deleteUser(userId);
		log.info("Удален пользователь: " + responseUser);

		log.info("Начато удаление владельца. Получен id: " + userId);
		Long ownerId = responseUser.getId();
		deleteOwner(ownerId);
		log.info("Удален владелец id: " + userId);

		return responseUser;
	}

	@Override
	public List<User> getAllUsers() {
		log.info("Начато получение всех пользователей.");
		List<User> responseUsersList = userRepository.getAllUsers();
		log.info("Получен список всех пользователей: " + responseUsersList);

		return responseUsersList;
	}

	@Override
	public List<User> deleteAllUsers() {
		log.info("Начато удаление всех пользователей.");
		List<User> responseUsersList = userRepository.deleteAllUsers();
		log.info("Получен список удаленных пользователей: " + responseUsersList);

		deleteAllOwners();

		return responseUsersList;
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

	private void deleteAllOwners() {
		log.info("Начато удаление всех владельцев.");
		if (itemRepository.deleteAllOwners()) {
			log.info("Все пользователи удалены.");
		} else {
			log.info("Неудачная попытка удаления всех пользователей.");
		}
	}
}