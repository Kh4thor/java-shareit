package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.ResponseUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.exception.UserException;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserException userException;

	@Mock
	private UserRepository userRepository;

	@Mock
	private ItemRepository itemRepository;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	void createUser_ShouldReturnResponseUserDto() {
		CreateUserDto createDto = new CreateUserDto();
		createDto.setName("Test User");
		createDto.setEmail("test@email.com");

		User savedUser = new User();
		savedUser.setId(1L);
		savedUser.setName("Test User");
		savedUser.setEmail("test@email.com");

		doNothing().when(userException).checkEmailAlreadyExistsException(anyString(), anyString());
		when(userRepository.save(any(User.class))).thenReturn(savedUser);

		ResponseUserDto result = userService.createUser(createDto);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("Test User", result.getName());
		assertEquals("test@email.com", result.getEmail());
		verify(userRepository, times(1)).save(any(User.class));
		verify(userException, times(1)).checkEmailAlreadyExistsException(anyString(), anyString());
	}

	@Test
	void getUser_ShouldReturnResponseUserDto() {
		Long userId = 1L;
		User user = new User();
		user.setId(userId);
		user.setName("Test User");
		user.setEmail("test@email.com");

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		ResponseUserDto result = userService.getUser(userId);

		assertNotNull(result);
		assertEquals(userId, result.getId());
		assertEquals("Test User", result.getName());
		assertEquals("test@email.com", result.getEmail());
		verify(userRepository, times(1)).findById(userId);
	}

	@Test
	void getUser_ShouldThrowExceptionWhenUserNotFound() {
		Long userId = 1L;

		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> userService.getUser(userId));
		verify(userRepository, times(1)).findById(userId);
	}

	@Test
	void updateUser_ShouldReturnUpdatedResponseUserDto() {
		UpdateUserDto updateDto = new UpdateUserDto();
		updateDto.setUserId(1L);
		updateDto.setName("Updated User");
		updateDto.setEmail("updated@email.com");

		User existingUser = new User();
		existingUser.setId(1L);
		existingUser.setName("Old User");
		existingUser.setEmail("old@email.com");

		User updatedUser = new User();
		updatedUser.setId(1L);
		updatedUser.setName("Updated User");
		updatedUser.setEmail("updated@email.com");

		when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
		doNothing().when(userException).checkEmailAlreadyExistsException(anyString(), anyString());
		when(userRepository.save(any(User.class))).thenReturn(updatedUser);

		ResponseUserDto result = userService.updateUser(updateDto);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("Updated User", result.getName());
		assertEquals("updated@email.com", result.getEmail());

		verify(userRepository, times(2)).findById(1L);
		verify(userRepository, times(1)).save(any(User.class));
		verify(userException, times(1)).checkEmailAlreadyExistsException(anyString(), anyString());
	}

	@Test
	void updateUser_WithSameEmail_ShouldNotCheckEmailUniqueness() {
		UpdateUserDto updateDto = new UpdateUserDto();
		updateDto.setUserId(1L);
		updateDto.setName("Updated User");
		updateDto.setEmail("same@email.com");

		User existingUser = new User();
		existingUser.setId(1L);
		existingUser.setName("Old User");
		existingUser.setEmail("same@email.com");

		User updatedUser = new User();
		updatedUser.setId(1L);
		updatedUser.setName("Updated User");
		updatedUser.setEmail("same@email.com");

		when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
		when(userRepository.save(any(User.class))).thenReturn(updatedUser);

		ResponseUserDto result = userService.updateUser(updateDto);

		assertNotNull(result);
		assertEquals("Updated User", result.getName());
		verify(userException, never()).checkEmailAlreadyExistsException(anyString(), anyString());
	}

	@Test
	void getAllUsers_ShouldReturnListOfUsers() {
		User user = new User();
		user.setId(1L);
		user.setName("Test User");
		user.setEmail("test@email.com");

		when(userRepository.findAll()).thenReturn(List.of(user));

		List<ResponseUserDto> result = userService.getAllUsers();

		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
		assertEquals("Test User", result.get(0).getName());
		verify(userRepository, times(1)).findAll();
	}

	@Test
	void deleteUser_ShouldCallRepositoryDelete() {
		Long userId = 1L;

		doNothing().when(userException).checkUserNotFoundException(anyLong(), anyString());
		doNothing().when(userRepository).deleteById(userId);

		userService.deleteUser(userId);

		verify(userException, times(1)).checkUserNotFoundException(userId, "Невозможно удалить пользователя.");
		verify(userRepository, times(1)).deleteById(userId);
	}

	@Test
	void deleteAllUsers_ShouldCallRepositoryDeleteAll() {
		doNothing().when(userRepository).deleteAll();
		doNothing().when(itemRepository).deleteAll();

		userService.deleteAllUsers();

		verify(userRepository, times(1)).deleteAll();
		verify(itemRepository, times(1)).deleteAll();
	}

	@Test
	void isUserOwnerOfEmail_ShouldReturnTrueForOwnEmail() {
		Long userId = 1L;
		String email = "test@email.com";

		User user = new User();
		user.setId(userId);
		user.setEmail(email);

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		Boolean result = userService.isUserOwnerOfEmail(userId, email);

		assertTrue(result);
		verify(userRepository, times(1)).findById(userId);
	}

	@Test
	void isUserOwnerOfEmail_ShouldReturnFalseForDifferentEmail() {
		Long userId = 1L;

		User user = new User();
		user.setId(userId);
		user.setEmail("user@email.com");

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		Boolean result = userService.isUserOwnerOfEmail(userId, "different@email.com");

		assertFalse(result);
		verify(userRepository, times(1)).findById(userId);
	}
}