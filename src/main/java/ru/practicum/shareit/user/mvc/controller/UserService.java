package ru.practicum.shareit.user.mvc.controller;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.exception.UserException;
import ru.practicum.shareit.user.mvc.model.User;
import ru.practicum.shareit.user.utils.UserMapper;

@Slf4j
@Service
public class UserService {

	private final UserException userException;
	private final UserRepository userRepository;

	public UserService(UserException userValidator, UserRepository userRepository) {
		this.userException = userValidator;
		this.userRepository = userRepository;
	}

	public UserDto createUser(UserDto userDto) {
		String errorMessage = "Невозможно создать пользователя.";
		userException.checkEmailAllreadyExistsException(userDto.getEmail(), errorMessage);

		log.info("Начато создание пользователя. Получен объект: " + userDto);
		User createdUser = userRepository.createUser(userDto);
		log.info("Создан пользователь: " + createdUser);
		UserDto createdUserDto = UserMapper.toUserDto(createdUser);
		log.info("Создан dto пользователя: " + createdUserDto);
		return createdUserDto;
	}
	
	public User updateUser(User user) {
		String errorMessage = "Невозможно обновить пользователя.";
		userException.checkUserAleradyExistsException(user.getId(), errorMessage);

		log.info("Начато обновление пользователя. Получен объект: " + user);
		User updatedUser = userRepository.updateUser(user);
		log.info("Обновлен пользователь: " + updatedUser);
		return updatedUser;
	}

	public User getUser(Long userId) {
		String errorMessage = "Невозможно получить пользователя.";
		userException.checkUserNotFoundException(userId, errorMessage);

		log.info("Начато получение пользователя. Получен id: " + userId);
		User getedUser = userRepository.getUser(userId);
		log.info("Получен пользователь: " + getedUser);
		return getedUser;
	}

	public User deleteUser(Long userId) {
		String errorMessage = "Невозможно удалить пользователя.";
		userException.checkUserNotFoundException(userId, errorMessage);

		log.info("Начато удаление пользователя. Получен id: " + userId);
		User deletedUser = userRepository.deleteUser(userId);
		log.info("Удален пользователь: " + deletedUser);
		return deletedUser;
	}

	public List<User> getAllUsers() {
		log.info("Начато получение всех пользователей.");
		List<User> allUsersList = userRepository.getAllUsers();
		log.info("Получен список всех пользователей: " + allUsersList);
		return allUsersList;
	}

	public List<User> deleteAllUsers() {
		log.info("Начато удаление всех пользователей.");
		List<User> listOfUsersToDelete = userRepository.deleteAllUsers();
		log.info("Получен список удаленных пользователей: " + listOfUsersToDelete);
		return listOfUsersToDelete;
	}
}
