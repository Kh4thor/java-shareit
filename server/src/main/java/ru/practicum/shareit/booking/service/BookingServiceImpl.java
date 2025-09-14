package ru.practicum.shareit.booking.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.booking.dto.CreateBookingDto;
import ru.practicum.shareit.booking.dto.ParamsDto;
import ru.practicum.shareit.booking.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.exception.BookingNotFoundException;
import ru.practicum.shareit.booking.exception.WrongBookingStatusException;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.utills.BookingMapper;
import ru.practicum.shareit.booking.utills.BookingStatus;
import ru.practicum.shareit.item.exception.ItemDoesNotBelongToTheOwnerException;
import ru.practicum.shareit.item.exception.ItemIsUnavailableException;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.exception.UserNotOwnerOfItemException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

	private final BookingRepository bookingRepository;
	private final ItemRepository itemRepository;
	private final UserRepository userRepository;

	public BookingServiceImpl(BookingRepository bookingRepository, ItemRepository itemRepository,
							  UserRepository userRepository) {
		this.bookingRepository = bookingRepository;
		this.itemRepository = itemRepository;
		this.userRepository = userRepository;
	}

	@Override
	public ResponseBookingDto createBooking(CreateBookingDto createBookingDto) {
		String errorMessage = "Невозможно создать бронирование";
		Booking booking = createBookingDtoToBooking(createBookingDto, errorMessage);
		booking.setStatus(BookingStatus.WAITING);

		log.info("Начато сохранение объекта " + booking);
		Booking createdBooking = bookingRepository.save(booking);
		log.info("Сохранен объект " + createdBooking);

		log.info("Начато преобразование объекта " + createdBooking + " в объект (ResponseBookingDto)responseBookingDto");
		ResponseBookingDto responseBooking = BookingMapper.bookingToResponseBookingDto(createdBooking);
		log.info("Закончено преобразование. Получен объект " + responseBooking);

		return responseBooking;
	}

	@Override
	public ResponseBookingDto getBooking(Long bookingId) {
		String errorMessage = "Невозможно получить бронирование.";
		Booking responseBooking = getBooking(bookingId, errorMessage);
		ResponseBookingDto responseBookingDto = BookingMapper.bookingToResponseBookingDto(responseBooking);
		return responseBookingDto;
	}

	@Override
	public void deleteBooking(ParamsDto paramsDto) {

		Long paramsOwnerId = paramsDto.getUserId();
		Long paramsBookingId = paramsDto.getBookingId();

		String errorMessage = "Невозможно удалить бронирование";
		Booking booking = getBooking(paramsBookingId, errorMessage);
		Item item = booking.getItem();
		Long itemId = item.getId();
		Long ownerId = item.getOwner().getId();

		if (!paramsOwnerId.equals(ownerId)) {
			throw new UserNotOwnerOfItemException(paramsOwnerId, itemId, errorMessage);
		}
		bookingRepository.deleteById(paramsBookingId);
	}

	@Override
	public Booking createBookingDtoToBooking(CreateBookingDto createBookingDto, String errorMessage) {
		Long itemId = createBookingDto.getItemId();
		Item item = getItem(itemId, errorMessage);

		Long bookerId = createBookingDto.getBookerId();
		User booker = getUser(bookerId, errorMessage);

		if (!item.getAvailable()) {
			throw new ItemIsUnavailableException(item, errorMessage);
		}

		Booking booking = BookingMapper.createBookingDtoToBooking(createBookingDto);
		booking.setItem(item);
		booking.setBooker(booker);
		return booking;
	}

	@Override
	public ResponseBookingDto setApprove(ParamsDto paramsDto) {
		Long bookingId = paramsDto.getBookingId();
		Long ownerId = paramsDto.getUserId();
		Boolean approve = paramsDto.getApprove();
		String errorMessage = "Невозможно подтвердить или отклонить бронирование.";
		Booking booking = getBooking(bookingId, errorMessage);

		if (!Objects.equals(booking.getItem().getOwner().getId(), ownerId)) {
			throw new ItemDoesNotBelongToTheOwnerException(booking.getItem().getId(), paramsDto.getUserId(),
					errorMessage);
		}
		if (!booking.getStatus().equals(BookingStatus.WAITING)) {
			throw new WrongBookingStatusException(booking.getStatus(), BookingStatus.WAITING, errorMessage);
		}
		BookingStatus status = approve ? BookingStatus.APPROVED : BookingStatus.REJECTED;
		booking.setStatus(status);
		Booking createdBooking = bookingRepository.save(booking);
		return BookingMapper.bookingToResponseBookingDto(createdBooking);
	}

	@Override
	public List<ResponseBookingDto> getAllBookingsOfUser(ParamsDto paramsDto) {
		Long userId = paramsDto.getUserId();
		String state = validateAndNormalizeState(paramsDto.getState());
		List<Booking> bookings = bookingRepository.findByUserIdAndState(userId, state);
		return bookings.stream().map(BookingMapper::bookingToResponseBookingDto).toList();
	}

	@Override
	public List<ResponseBookingDto> getAllBookingsOfOwner(ParamsDto paramsDto) {
		String errorMessage = "Невозможно получить список бронирований пользователя";
		Long ownerId = paramsDto.getUserId();
		@SuppressWarnings("unused")
		User user = getUser(ownerId, errorMessage); // валидация пользователя
		String state = validateAndNormalizeState(paramsDto.getState());
		List<Booking> bookings = bookingRepository.findByOwnerIdAndState(ownerId, state);
		return	bookings.stream()
				.map(BookingMapper::bookingToResponseBookingDto)
				.toList();
	}

	@Override
	public ResponseBookingDto getBookingOfOwner(ParamsDto paramsDto) {
		Long bookingId = paramsDto.getBookingId();
		Long ownerId = paramsDto.getUserId();
		String errorMessage = "Бронирование не найдено или не принадлежит пользователю";
		Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(bookingId, errorMessage));

		if (!booking.getBooker().getId().equals(ownerId) && !booking.getItem().getOwner().getId().equals(ownerId)) {
			throw new BookingNotFoundException(bookingId, errorMessage);
		}
		return BookingMapper.bookingToResponseBookingDto(booking);
	}

	private User getUser(Long userId, String errorMessage) {
		return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId, errorMessage));
	}

	private Item getItem(Long itemId, String errorMessage) {
		return itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId, errorMessage));
	}

	private Booking getBooking(Long bookingId, String errorMessage) {
		return bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(bookingId, errorMessage));
	}

	private String validateAndNormalizeState(String state) {
		List<String> validStates = List.of("ALL", "CURRENT", "PAST", "FUTURE", "WAITING", "REJECTED");
		state = state == null ? "ALL" : state;
		state = state.toUpperCase();
		state = validStates.contains(state) ? state : "ALL";
		return state;
	}
}
