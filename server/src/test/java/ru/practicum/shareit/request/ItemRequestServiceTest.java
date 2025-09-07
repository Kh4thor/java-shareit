package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.mvc.controller.repository.BookingRepositoryApp;
import ru.practicum.shareit.item.mvc.controller.repository.CommentRepositoryApp;
import ru.practicum.shareit.item.mvc.controller.repository.ItemRepositoryApp;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.GetItemRequestDto;
import ru.practicum.shareit.request.dto.ResponseItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.mvc.controller.repository.UserRepositoryApp;
import ru.practicum.shareit.user.mvc.model.User;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemRequestServiceTest {

    @Mock
    private ItemRequestRepositoryApp itemRequestRepository;

    @Mock
    private UserRepositoryApp userRepository;

    @Mock
    private ItemRepositoryApp itemRepository;

    @Mock
    private BookingRepositoryApp bookingRepository;

    @Mock
    private CommentRepositoryApp commentRepository;

    @InjectMocks
    private ItemRequestService itemRequestService;

    @Test
    void createItemRequest_ShouldReturnResponseItemRequestDto() {
        CreateItemRequestDto createDto = new CreateItemRequestDto();
        createDto.setOwnerId(1L);
        createDto.setDescription("Test request");

        User user = new User();
        user.setId(1L);

        ItemRequest savedRequest = new ItemRequest();
        savedRequest.setId(1L);
        savedRequest.setDescription("Test request");
        savedRequest.setRequestor(user);
        savedRequest.setCreated(LocalDateTime.now());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(itemRequestRepository.save(any(ItemRequest.class))).thenReturn(savedRequest);

        ResponseItemRequestDto result = itemRequestService.createItemRequest(createDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test request", result.getDescription());
        verify(userRepository, times(1)).findById(1L);
        verify(itemRequestRepository, times(1)).save(any(ItemRequest.class));
    }

    @Test
    void getItemRequest_ShouldReturnResponseItemRequestDtoWithItems() {
        GetItemRequestDto getDto = new GetItemRequestDto();
        getDto.setItemRequestId(1L);
        getDto.setOwnerId(1L);

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setId(1L);
        itemRequest.setDescription("Test request");

        when(itemRequestRepository.findById(1L)).thenReturn(Optional.of(itemRequest));
        when(itemRepository.findByOwnerId(1L)).thenReturn(List.of());

        ResponseItemRequestDto result = itemRequestService.getItemRequest(getDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test request", result.getDescription());
        assertNotNull(result.getItems());
        verify(itemRequestRepository, times(1)).findById(1L);
        verify(itemRepository, times(1)).findByOwnerId(1L);
    }
}