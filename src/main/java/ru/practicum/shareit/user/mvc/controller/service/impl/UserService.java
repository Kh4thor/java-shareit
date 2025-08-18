package ru.practicum.shareit.user.mvc.controller.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.mvc.controller.repository.ItemRepositoryApp;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.ResponseUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.exception.UserException;
import ru.practicum.shareit.user.exception.UserNotFoundException;
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
	public ResponseUserDto createUser(@NotNull CreateUserDto createUserDto) {
		String errorMessage = "Неудачная попытка создать пользователя.";
		userException.checkEmailAlreadyExistsException(createUserDto.getEmail(), errorMessage);

		log.info("Начато преобразование (CreateUserDto)createUserDto в объект класса User. Получен объект: " + createUserDto);
		User createuser = UserMapper.createUserDtoToUser(createUserDto);
		log.info("createUserDto преобразован в объект класса User: " + createuser);

		log.info("Начато создание пользователя. Получен объект: " + createuser);
		User responseUser = userRepository.save(createuser);
		log.info("Создан пользователь: " + responseUser);

		log.info("Начато преобразование (User)responseUser в объект класса ResponseUserDto. Получен объект: " + responseUser);
		ResponseUserDto responseUserDto = UserMapper.userToResponseUserDto(responseUser);
		log.info("(User)responseUser преобразован в объект класса ResponseUserDto: " + responseUser);

		return responseUserDto;
	}

	public Boolean isUserOwnerOfEmail(Long userId, String email) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId, null));
		return user.getEmail().equals(email);
	}

	@Override
	@Transactional
	public ResponseUserDto updateUser(UpdateUserDto updateUserDto) {
		Long userId = updateUserDto.getUserId();
		String email = updateUserDto.getEmail();

		String errorMessage = "Невозможно обновить пользователя.";
		userException.checkUserNotFoundException(userId, errorMessage);
		if (!isUserOwnerOfEmail(userId, email)) {
			userException.checkEmailAlreadyExistsException(email, errorMessage);
		}

		log.info("Начато преобразование (UpdateUserDto)updateUserDto в объект класса User. Получен объект: " + updateUserDto);
		User updateUser = UserMapper.updateUserDtoToUser(updateUserDto);
		log.info("updateUserDto преобразован в объект класса User: " + updateUser);

		log.info("Начато обновление пользователя. Получен объект: " + updateUser);
		User responseUser = userRepository.save(updateUser);
		log.info("Обновлен пользователь: " + responseUser);

		log.info("Начато преобразование (User)responseUser в объект класса ResponseUserDto. Получен объект: " + responseUser);
		ResponseUserDto responseUserDto = UserMapper.userToResponseUserDto(responseUser);
		log.info("(User)responseUser преобразован в объект класса ResponseUserDto: " + responseUser);

		return responseUserDto;
	}

	@Override
	public ResponseUserDto getUser(Long userId) {
		String errorMessage = "Невозможно получить пользователя.";

		log.info("Начато получение пользователя. Получен id: " + userId);
		User responseUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId, errorMessage));
		log.info("Получен пользователь: " + responseUser);

		log.info("Начато преобразование (User)responseUser в объект класса ResponseUserDto. Получен объект: " + responseUser);
		ResponseUserDto responseUserDto = UserMapper.userToResponseUserDto(responseUser);
		log.info("(User)responseUser преобразован в объект класса ResponseUserDto: " + responseUser);

		return responseUserDto;
	}

	@Override
	@Transactional
	public void deleteUser(@NotNull Long userId) {
		String errorMessage = "Невозможно удалить пользователя.";
		userException.checkUserNotFoundException(userId, errorMessage);

		log.info("Начато удаление пользователя. Получен id: " + userId);
		userRepository.deleteById(userId);
		log.info("Удален пользователь id=: " + userId);

	}

	@Override
	public List<ResponseUserDto> getAllUsers() {
		log.info("Начато получение всех пользователей.");
		List<User> responseUsersList = userRepository.findAll();
		log.info("Получен список всех пользователей: " + responseUsersList);

		log.info("Начато преобразование списка (User)responseUser в объекты класса ResponseUserDto. Получен список объектов: " + responseUsersList);
		List<ResponseUserDto> responseUserDtoList = responseUsersList
													.stream()
													.map(UserMapper::userToResponseUserDto)
													.toList();
		log.info("Список объектов (User)responseUser преобразован в объекты класса ResponseUserDto: " + responseUserDtoList);

		return responseUserDtoList;
	}

	@Override
	public void deleteAllUsers() {
		log.info("Начато удаление всех пользователей.");
		userRepository.deleteAll();
		log.info("Все пользователи удалены.");

		log.info("Начато удаление всех предметов.");
		itemRepository.deleteAll();
		log.info("Все предметы удалены.");
	}
}