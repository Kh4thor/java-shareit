package ru.practicum.shareit.owner;

import ru.practicum.shareit.user.exception.UserException;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

public class OwnerService extends UserService {

	public OwnerService(UserException userValidator, UserRepository userRepository) {
		super(userValidator, userRepository);
	}



}
