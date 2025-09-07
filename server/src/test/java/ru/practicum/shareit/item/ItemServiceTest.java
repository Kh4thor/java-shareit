package ru.practicum.shareit.item.mvc.controller.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.mvc.controller.repository.BookingRepositoryApp;
import ru.practicum.shareit.item.dto.CreateCommentDto;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ResponseItemDto;
import ru.practicum.shareit.item.mvc.controller.repository.CommentRepositoryApp;
import ru.practicum.shareit.item.mvc.controller.repository.ItemRepositoryApp;
import ru.practicum.shareit.item.mvc.model.Comment;
import ru.practicum.shareit.item.mvc.model.Item;
import ru.practicum.shareit.user.exception.UserException;
import ru.practicum.shareit.user.exception.UserNotBookerOfItemException;
import ru.practicum.shareit.user.mvc.controller.repository.UserRepositoryApp;
import ru.practicum.shareit.user.mvc.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

	@Mock
	private UserException userException;

	@Mock
	private ItemRepositoryApp itemRepository;

	@Mock
	private UserRepositoryApp userRepository;

	@Mock
	private CommentRepositoryApp commentRepository;

	@Mock
	private BookingRepositoryApp bookingRepository;

	@InjectMocks
	private ItemService itemService;

	@Test
	void createItem_ShouldReturnResponseItemDto() {
		CreateItemDto createDto = new CreateItemDto();
		createDto.setOwnerId(1L);
		createDto.setName("Test Item");
		createDto.setDescription("Test Description");
		createDto.setAvailable(true);

		User owner = new User();
		owner.setId(1L);

		Item savedItem = new Item();
		savedItem.setId(1L);
		savedItem.setName("Test Item");
		savedItem.setOwner(owner);

		when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
		when(itemRepository.save(any(Item.class))).thenReturn(savedItem);

		ResponseItemDto result = itemService.createItem(createDto);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("Test Item", result.getName());
		verify(userRepository, times(1)).findById(1L);
		verify(itemRepository, times(1)).save(any(Item.class));
	}

	@Test
	void getItemWithComments_ShouldReturnResponseItemDtoWithComments() {
		Long itemId = 1L;

		User owner = new User();
		owner.setId(1L);
		owner.setName("Test Owner");

		Item item = new Item();
		item.setId(itemId);
		item.setName("Test Item");
		item.setOwner(owner);

		User commentator = new User();
		commentator.setId(2L);
		commentator.setName("Test Commentator");
		commentator.setEmail("commentator@test.com");

		Comment comment = new Comment();
		comment.setId(1L);
		comment.setText("Test comment");
		comment.setItem(item);
		comment.setCommentator(commentator);
		comment.setCreated(LocalDateTime.now());

		when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
		when(commentRepository.findCommentsByItemId(itemId)).thenReturn(List.of(comment));

		ResponseItemDto result = itemService.getItemWithComments(itemId);

		assertNotNull(result);
		assertEquals(itemId, result.getId());
		assertFalse(result.getComments().isEmpty());
		assertEquals(1, result.getComments().size());
		assertEquals("Test comment", result.getComments().get(0).getText());
		verify(itemRepository, times(1)).findById(itemId);
		verify(commentRepository, times(1)).findCommentsByItemId(itemId);
	}

	@Test
	void createComment_ShouldThrowExceptionWhenUserNotBooker() {
		CreateCommentDto createDto = new CreateCommentDto();
		createDto.setCommentatorId(1L);
		createDto.setItemId(1L);
		createDto.setText("Test comment");

		User commentator = new User();
		commentator.setId(1L);

		Item item = new Item();
		item.setId(1L);

		when(userRepository.findById(1L)).thenReturn(Optional.of(commentator));
		when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
		when(bookingRepository.findByUserIdAndState(1L, "ALL")).thenReturn(List.of());

		assertThrows(UserNotBookerOfItemException.class, () -> itemService.createComment(createDto));
	}
}