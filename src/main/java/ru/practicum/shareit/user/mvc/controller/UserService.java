package ru.practicum.shareit.user.mvc.controller;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.mvc.controller.ItemRepositoryApp;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.ResponseUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.exception.UserException;
import ru.practicum.shareit.user.mvc.model.User;
import ru.practicum.shareit.user.utils.ResponseUserDtoMapper;
import ru.practicum.shareit.user.utils.UserMapper;

@Slf4j
@Service
public class UserService {

	private final UserException userException;
	private final UserRepositoryApp userRepository;
	private final ItemRepositoryApp itemRepository;

	public UserService(UserException userValidator, UserRepositoryApp userRepository, ItemRepositoryApp itemRepository) {
		this.userException = userValidator;
		this.userRepository = userRepository;
		this.itemRepository = itemRepository;
	}

	public ResponseUserDto createUser(@NotNull CreateUserDto createUserDto) {
		String errorMessage = "Невозможно создать пользователя.";
		userException.checkEmailAlreadyExistsException(createUserDto.getEmail(), errorMessage);

		log.info("Начато преобразование createUserDto в объект класса User. Получен объект: " + createUserDto);
		User createuser = UserMapper.createUserDtoToUser(createUserDto);
		log.info("createUserDto преобразован в объект класса User: " + createuser);
		
		log.info("Начато создание пользователя. Получен объект: " + createuser);
		User responseUser = userRepository.createUser(createuser);
		log.info("Создан пользователь: " + responseUser);
		
		log.info("Начато преобразование responseUser в объект класса ResponseUserDto. Получен объект: " + responseUser);
		ResponseUserDto responseUserDto = UserMapper.userToResponseUserDto(responseUser);
		log.info("createUserDto преобразован в объект класса User: " + createuser);
		
		return responseUserDto;
	}

	public ResponseUserDto updateUser(UpdateUserDto updateUserDto) {
		Long userId = updateUserDto.getUserId();
		String email = updateUserDto.getEmail();
		
		String errorMessage = "Невозможно обновить пользователя.";
		userException.checkUserNotFoundException(userId, errorMessage);
		if (userRepository.isUserOwnerOfEmail(userId, email) == false ) {
			userException.checkEmailAlreadyExistsException(email, errorMessage);
		}

		log.info("Начато преобразование updateUserDto в объект класса User. Получен объект: " + updateUserDto);
		User user = UserMapper.updateUserDtoUser(updateUserDto);
		log.info("createUserDto преобразован в объект класса User: " + user);

		User responseUser = userRepository.updateUser(updateUserDto);
		ResponseUserDto responseUserDto = ResponseUserDtoMapper.toResponseUserDto(user);
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