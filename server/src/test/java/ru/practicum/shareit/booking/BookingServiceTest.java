package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.exception.BookingNotFoundException;
import ru.practicum.shareit.booking.exception.WrongBookingStatusException;
import ru.practicum.shareit.booking.mvc.controller.repository.BookingRepositoryApp;
import ru.practicum.shareit.booking.mvc.controller.service.impl.BookingService;
import ru.practicum.shareit.booking.mvc.model.Booking;
import ru.practicum.shareit.booking.mvc.model.dto.CreateBookingDto;
import ru.practicum.shareit.booking.mvc.model.dto.ParamsDto;
import ru.practicum.shareit.booking.mvc.model.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.utills.BookingStatus;
import ru.practicum.shareit.item.exception.ItemDoesNotBelongToTheOwnerException;
import ru.practicum.shareit.item.exception.ItemIsUnavailableException;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.mvc.controller.repository.ItemRepositoryApp;
import ru.practicum.shareit.item.mvc.model.Item;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.exception.UserNotOwnerOfItemException;
import ru.practicum.shareit.user.mvc.controller.repository.UserRepositoryApp;
import ru.practicum.shareit.user.mvc.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

	@Mock
	private BookingRepositoryApp bookingRepository;

	@Mock
	private ItemRepositoryApp itemRepository;

	@Mock
	private UserRepositoryApp userRepository;

	@InjectMocks
	private BookingService bookingService;

	@Test
	void createBooking_WhenValidData_ShouldReturnResponseBookingDto() {
		CreateBookingDto createBookingDto = new CreateBookingDto();
		createBookingDto.setItemId(1L);
		createBookingDto.setBookerId(1L);
		createBookingDto.setStart(LocalDateTime.now().plusDays(1));
		createBookingDto.setEnd(LocalDateTime.now().plusDays(2));

		User owner = new User(2L, "owner", "owner@email.com");
		Item item = new Item();
		item.setId(1L);
		item.setName("Test Item");
		item.setDescription("Test Description");
		item.setAvailable(true);
		item.setOwner(owner);

		User booker = new User(1L, "booker", "booker@email.com");
		Booking savedBooking = new Booking();
		savedBooking.setId(1L);
		savedBooking.setItem(item);
		savedBooking.setBooker(booker);
		savedBooking.setStatus(BookingStatus.WAITING);
		savedBooking.setStart(createBookingDto.getStart());
		savedBooking.setEnd(createBookingDto.getEnd());

		when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
		when(userRepository.findById(1L)).thenReturn(Optional.of(booker));
		when(bookingRepository.save(any(Booking.class))).thenReturn(savedBooking);

		ResponseBookingDto result = bookingService.createBooking(createBookingDto);

		assertNotNull(result);
		verify(bookingRepository).save(any(Booking.class));
	}

	@Test
	void createBooking_WhenItemNotFound_ShouldThrowItemNotFoundException() {
		CreateBookingDto createBookingDto = new CreateBookingDto();
		createBookingDto.setItemId(1L);
		createBookingDto.setBookerId(1L);

		when(itemRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ItemNotFoundException.class, () -> bookingService.createBooking(createBookingDto));
	}

	@Test
	void createBooking_WhenUserNotFound_ShouldThrowUserNotFoundException() {
		CreateBookingDto createBookingDto = new CreateBookingDto();
		createBookingDto.setItemId(1L);
		createBookingDto.setBookerId(1L);

		User owner = new User(2L, "owner", "owner@email.com");
		Item item = new Item();
		item.setId(1L);
		item.setName("Test Item");
		item.setDescription("Test Description");
		item.setAvailable(true);
		item.setOwner(owner);

		when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> bookingService.createBooking(createBookingDto));
	}

	@Test
	void createBooking_WhenItemUnavailable_ShouldThrowItemIsUnavailableException() {
		CreateBookingDto createBookingDto = new CreateBookingDto();
		createBookingDto.setItemId(1L);
		createBookingDto.setBookerId(1L);

		User owner = new User(2L, "owner", "owner@email.com");
		Item item = new Item();
		item.setId(1L);
		item.setName("Test Item");
		item.setDescription("Test Description");
		item.setAvailable(false);
		item.setOwner(owner);

		User booker = new User(1L, "booker", "booker@email.com");

		when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
		when(userRepository.findById(1L)).thenReturn(Optional.of(booker));

		assertThrows(ItemIsUnavailableException.class, () -> bookingService.createBooking(createBookingDto));
	}

	@Test
	void getBooking_WhenBookingExists_ShouldReturnResponseBookingDto() {
		// given
		Long bookingId = 1L;

		User owner = new User(2L, "owner", "owner@email.com");
		User booker = new User(1L, "booker", "booker@email.com");

		Item item = new Item();
		item.setId(1L);
		item.setName("Test Item");
		item.setDescription("Test Description");
		item.setAvailable(true);
		item.setOwner(owner);

		Booking booking = new Booking();
		booking.setId(bookingId);
		booking.setItem(item);
		booking.setBooker(booker);
		booking.setStatus(BookingStatus.APPROVED);
		booking.setStart(LocalDateTime.now().minusDays(1));
		booking.setEnd(LocalDateTime.now().plusDays(1));

		when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

		ResponseBookingDto result = bookingService.getBooking(bookingId);

		assertNotNull(result);
		verify(bookingRepository).findById(bookingId);
	}

	@Test
	void getBooking_WhenBookingNotFound_ShouldThrowBookingNotFoundException() {
		Long bookingId = 1L;
		when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

		assertThrows(BookingNotFoundException.class, () -> bookingService.getBooking(bookingId));
	}

	@Test
	void setApprove_WhenValidApproval_ShouldReturnApprovedBooking() {
		ParamsDto paramsDto = new ParamsDto();
		paramsDto.setBookingId(1L);
		paramsDto.setUserId(2L);
		paramsDto.setApprove(true);

		User owner = new User(2L, "owner", "owner@email.com");
		User booker = new User(1L, "booker", "booker@email.com");

		Item item = new Item();
		item.setId(1L);
		item.setName("Test Item");
		item.setDescription("Test Description");
		item.setAvailable(true);
		item.setOwner(owner);

		Booking booking = new Booking();
		booking.setId(1L);
		booking.setItem(item);
		booking.setBooker(booker);
		booking.setStatus(BookingStatus.WAITING);
		booking.setStart(LocalDateTime.now().minusDays(1));
		booking.setEnd(LocalDateTime.now().plusDays(1));

		when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
		when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

		ResponseBookingDto result = bookingService.setApprove(paramsDto);

		assertNotNull(result);
		verify(bookingRepository).save(any(Booking.class));
	}

	@Test
	void setApprove_WhenNotOwner_ShouldThrowItemDoesNotBelongToTheOwnerException() {
		ParamsDto paramsDto = new ParamsDto();
		paramsDto.setBookingId(1L);
		paramsDto.setUserId(999L);
		paramsDto.setApprove(true);

		User owner = new User(2L, "owner", "owner@email.com");
		User booker = new User(1L, "booker", "booker@email.com");

		Item item = new Item();
		item.setId(1L);
		item.setName("Test Item");
		item.setDescription("Test Description");
		item.setAvailable(true);
		item.setOwner(owner);

		Booking booking = new Booking();
		booking.setId(1L);
		booking.setItem(item);
		booking.setBooker(booker);
		booking.setStatus(BookingStatus.WAITING);
		booking.setStart(LocalDateTime.now().minusDays(1));
		booking.setEnd(LocalDateTime.now().plusDays(1));

		when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

		assertThrows(ItemDoesNotBelongToTheOwnerException.class, () -> bookingService.setApprove(paramsDto));
	}

	@Test
	void setApprove_WhenNotWaitingStatus_ShouldThrowWrongBookingStatusException() {
		ParamsDto paramsDto = new ParamsDto();
		paramsDto.setBookingId(1L);
		paramsDto.setUserId(2L);
		paramsDto.setApprove(true);

		User owner = new User(2L, "owner", "owner@email.com");
		User booker = new User(1L, "booker", "booker@email.com");

		Item item = new Item();
		item.setId(1L);
		item.setName("Test Item");
		item.setDescription("Test Description");
		item.setAvailable(true);
		item.setOwner(owner);

		Booking booking = new Booking();
		booking.setId(1L);
		booking.setItem(item);
		booking.setBooker(booker);
		booking.setStatus(BookingStatus.APPROVED); // Already approved
		booking.setStart(LocalDateTime.now().minusDays(1));
		booking.setEnd(LocalDateTime.now().plusDays(1));

		when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

		assertThrows(WrongBookingStatusException.class, () -> bookingService.setApprove(paramsDto));
	}

	@Test
	void getBookingOfOwner_WhenUserIsBooker_ShouldReturnBooking() {
		ParamsDto paramsDto = new ParamsDto();
		paramsDto.setBookingId(1L);
		paramsDto.setUserId(1L);

		User booker = new User(1L, "booker", "booker@email.com");
		User owner = new User(2L, "owner", "owner@email.com");

		Item item = new Item();
		item.setId(1L);
		item.setName("Test Item");
		item.setDescription("Test Description");
		item.setAvailable(true);
		item.setOwner(owner);

		Booking booking = new Booking();
		booking.setId(1L);
		booking.setItem(item);
		booking.setBooker(booker);
		booking.setStatus(BookingStatus.APPROVED);
		booking.setStart(LocalDateTime.now().minusDays(1));
		booking.setEnd(LocalDateTime.now().plusDays(1));

		when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

		ResponseBookingDto result = bookingService.getBookingOfOwner(paramsDto);

		assertNotNull(result);
	}

	@Test
	void getBookingOfOwner_WhenUserIsOwner_ShouldReturnBooking() {
		ParamsDto paramsDto = new ParamsDto();
		paramsDto.setBookingId(1L);
		paramsDto.setUserId(2L);

		User booker = new User(1L, "booker", "booker@email.com");
		User owner = new User(2L, "owner", "owner@email.com");

		Item item = new Item();
		item.setId(1L);
		item.setName("Test Item");
		item.setDescription("Test Description");
		item.setAvailable(true);
		item.setOwner(owner);

		Booking booking = new Booking();
		booking.setId(1L);
		booking.setItem(item);
		booking.setBooker(booker);
		booking.setStatus(BookingStatus.APPROVED);
		booking.setStart(LocalDateTime.now().minusDays(1));
		booking.setEnd(LocalDateTime.now().plusDays(1));

		when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

		ResponseBookingDto result = bookingService.getBookingOfOwner(paramsDto);

		assertNotNull(result);
	}

	@Test
	void getBookingOfOwner_WhenUserNotRelated_ShouldThrowBookingNotFoundException() {
		ParamsDto paramsDto = new ParamsDto();
		paramsDto.setBookingId(1L);
		paramsDto.setUserId(999L);

		User booker = new User(1L, "booker", "booker@email.com");
		User owner = new User(2L, "owner", "owner@email.com");

		Item item = new Item();
		item.setId(1L);
		item.setName("Test Item");
		item.setDescription("Test Description");
		item.setAvailable(true);
		item.setOwner(owner);

		Booking booking = new Booking();
		booking.setId(1L);
		booking.setItem(item);
		booking.setBooker(booker);
		booking.setStatus(BookingStatus.APPROVED);
		booking.setStart(LocalDateTime.now().minusDays(1));
		booking.setEnd(LocalDateTime.now().plusDays(1));

		when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

		assertThrows(BookingNotFoundException.class, () -> bookingService.getBookingOfOwner(paramsDto));
	}

	@Test
	void deleteBooking_WhenUserIsOwner_ShouldDeleteBooking() {
		ParamsDto paramsDto = new ParamsDto();
		paramsDto.setUserId(2L);
		paramsDto.setBookingId(1L);

		User owner = new User(2L, "owner", "owner@email.com");
		User booker = new User(1L, "booker", "booker@email.com");

		Item item = new Item();
		item.setId(1L);
		item.setName("Test Item");
		item.setDescription("Test Description");
		item.setAvailable(true);
		item.setOwner(owner);

		Booking booking = new Booking();
		booking.setId(1L);
		booking.setItem(item);
		booking.setBooker(booker);
		booking.setStatus(BookingStatus.APPROVED);
		booking.setStart(LocalDateTime.now().minusDays(1));
		booking.setEnd(LocalDateTime.now().plusDays(1));

		when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

		bookingService.deleteBooking(paramsDto);

		verify(bookingRepository).deleteById(1L);
	}

	@Test
	void deleteBooking_WhenUserNotOwner_ShouldThrowUserNotOwnerOfItemException() {
		ParamsDto paramsDto = new ParamsDto();
		paramsDto.setUserId(999L);
		paramsDto.setBookingId(1L);

		User owner = new User(2L, "owner", "owner@email.com");
		User booker = new User(1L, "booker", "booker@email.com");

		Item item = new Item();
		item.setId(1L);
		item.setName("Test Item");
		item.setDescription("Test Description");
		item.setAvailable(true);
		item.setOwner(owner);

		Booking booking = new Booking();
		booking.setId(1L);
		booking.setItem(item);
		booking.setBooker(booker);
		booking.setStatus(BookingStatus.APPROVED);
		booking.setStart(LocalDateTime.now().minusDays(1));
		booking.setEnd(LocalDateTime.now().plusDays(1));

		when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

		assertThrows(UserNotOwnerOfItemException.class, () -> bookingService.deleteBooking(paramsDto));
	}
}