package ru.practicum.shareit.user.mvc.controller;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.ResponseUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.exception.UserException;

@Slf4j
@Service
public class UserService {

	private final UserException userException;
	private final UserRepository userRepository;

	public UserService(UserException userValidator, UserRepository userRepository) {
		this.userException = userValidator;
		this.userRepository = userRepository;
	}

	public ResponseUserDto createUser(CreateUserDto userDto) {
		String errorMessage = "Невозможно создать пользователя.";
		userException.checkEmailAllreadyExistsException(userDto.getEmail(), errorMessage);

		log.info("Начато создание пользователя. Получен объект: " + userDto);
		ResponseUserDto createdUser = userRepository.createUser(userDto);
		log.info("Создан пользователь: " + createdUser);

		return createdUser;
	}
	
	public ResponseUserDto updateUser(UpdateUserDto userDto, Long userId) {
		String errorMessage = "Невозможно обновить пользователя.";
		userException.checkUserNotFoundException(userId, errorMessage);

		if (userDto.getName() != null & userDto.getEmail() == null) {
			return updateNameOfUser(userDto, userId);
		}
		if (userDto.getName() == null & userDto.getEmail() != null) {
			return updateEmailOfUser(userDto, userId, errorMessage);
		}
		if (userDto.getName() != null & userDto.getEmail() != null) {
			return updateNameAndEmailOfUser(userDto, userId, errorMessage);
		}
		throw new RuntimeErrorException(null, "Поля name и email не могут быть пустыми одновременно");
	}

	public ResponseUserDto getUser(Long userId) {
		String errorMessage = "Невозможно получить пользователя.";
		userException.checkUserNotFoundException(userId, errorMessage);

		log.info("Начато получение пользователя. Получен id: " + userId);
		ResponseUserDto getedUser = userRepository.getUser(userId);
		log.info("Получен пользователь: " + getedUser);
		return getedUser;
	}

	public ResponseUserDto deleteUser(Long userId) {
		String errorMessage = "Невозможно удалить пользователя.";
		userException.checkUserNotFoundException(userId, errorMessage);

		log.info("Начато удаление пользователя. Получен id: " + userId);
		ResponseUserDto deletedUser = userRepository.deleteUser(userId);
		log.info("Удален пользователь: " + deletedUser);
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

	private ResponseUserDto updateNameOfUser(UpdateUserDto userDto, Long userId) {
		ResponseUserDto updatedUser = userRepository.updateNameOfUser(userDto, userId);
		log.info("Обновлено имя пользователя: " + updatedUser);
		return updatedUser;
	}

	private ResponseUserDto updateEmailOfUser(UpdateUserDto userDto, Long userId, String errorMessage) {
		userException.checkEmailAllreadyExistsException(userDto.getEmail(), errorMessage);
		ResponseUserDto updatedUser = userRepository.updateEmailOfUser(userDto, userId);
		log.info("Обновлен email пользователя: " + updatedUser);
		return updatedUser;
	}

	private ResponseUserDto updateNameAndEmailOfUser(UpdateUserDto userDto, Long userId, String errorMessage) {
		log.info("Начато обновление пользователя. Получен объект: " + userDto);
		userException.checkEmailAllreadyExistsException(userDto.getEmail(), errorMessage);
		ResponseUserDto updatedUser = userRepository.updateUser(userDto, userId);
		log.info("Обновлен пользователь: " + updatedUser);
		return updatedUser;
	}
}
