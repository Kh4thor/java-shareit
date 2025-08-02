package ru.practicum.shareit.user.exception;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.user.mvc.controller.UserRepository;

@Slf4j
@Component
public class UserException {

	private final UserRepository userRepository;

	public UserException(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void checkEmailAlreadyExistsException(String email, String errorMessage) {
		if (userRepository.isEmailExists(email)) {
			RuntimeException exception = new EmailAllreadyExistsException(email, errorMessage);
			log.warn(errorMessage + " " + exception.getMessage());
			throw exception;
		}
	}

	public void checkUserNotFoundException(Long userId, String errorMessage) {
		if (!userRepository.isUserExists(userId)) {
			RuntimeException exception = new UserNotFoundException(userId, errorMessage);
			log.warn(errorMessage + " " + exception.getMessage());
			throw exception;
		}
	}

	public void checkUserAleradyExistsException(Long userId, String errorMessage) {
		if (userRepository.isUserExists(userId)) {
			RuntimeException exception = new UserAlreadyExistsException(userId, errorMessage);
			log.warn(errorMessage + " " + exception.getMessage());
			throw exception;
		}
	}
}
