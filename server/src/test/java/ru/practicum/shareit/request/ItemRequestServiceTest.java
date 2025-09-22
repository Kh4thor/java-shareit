package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.GetItemRequestDto;
import ru.practicum.shareit.request.dto.ResponseItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.request.service.ItemRequestServiceImpl;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemRequestServiceTest {

    @Mock
    private ItemRequestRepository itemRequestRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private ItemRequestServiceImpl itemRequestService;

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