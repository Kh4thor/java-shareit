package ru.practicum.shareit.booking.mvc.controller.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.booking.exception.BookingNotFoundException;
import ru.practicum.shareit.booking.mvc.controller.controller.ParamsDto;
import ru.practicum.shareit.booking.mvc.controller.repository.BookingRepositoryApp;
import ru.practicum.shareit.booking.mvc.controller.service.BookingServiceApp;
import ru.practicum.shareit.booking.mvc.model.Booking;
import ru.practicum.shareit.booking.mvc.model.dto.CreateBookingDto;
import ru.practicum.shareit.booking.mvc.model.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.utills.BookingMapper;
import ru.practicum.shareit.booking.utills.BookingStatus;
import ru.practicum.shareit.item.exception.ItemDoesNotBelongToTheOwnerException;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.mvc.controller.repository.ItemRepositoryApp;
import ru.practicum.shareit.item.mvc.model.Item;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.mvc.controller.repository.UserRepositoryApp;
import ru.practicum.shareit.user.mvc.model.User;

@Slf4j
@Service
public class BookingService implements BookingServiceApp {

	private final BookingRepositoryApp bookingRepository;
	private final ItemRepositoryApp itemRepository;
	private final UserRepositoryApp userRepository;

	public BookingService(BookingRepositoryApp bookingRepository, ItemRepositoryApp itemRepository,
			UserRepositoryApp userRepository) {
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

	public ResponseBookingDto getBooking(Long bookingId) {
		String errorMessage = "Невозможно получить бронирование.";
		Booking responseBooking = getBooking(bookingId, errorMessage);
		ResponseBookingDto responseBookingDto = BookingMapper.bookingToResponseBookingDto(responseBooking);
		return responseBookingDto;
	}

	@Override
	public void deleteBooking(Long bookingId) {
		bookingRepository.deleteById(bookingId);
	}

	@Override
	public void deleteAllBookings() {
		bookingRepository.deleteAll();
	}

	private Booking createBookingDtoToBooking(CreateBookingDto createBookingDto, String errorMessage) {
		Booking booking = BookingMapper.createBookingDtoToBooking(createBookingDto);

		Long itemId = createBookingDto.getItemId();
		Item item = getItem(itemId, errorMessage);
		booking.setItem(item);

		Long bookerId = createBookingDto.getBookerId();
		User booker = getUser(bookerId, errorMessage);
		booking.setBooker(booker);

		if (!item.getAvailable()) {
			throw new ItemIsUnavailableException(item, errorMessage);
		}
		return booking;
	}

	public ResponseBookingDto setApprove(ParamsDto paramsDto) {
		Long bookingId = paramsDto.getBookingId();
		Long ownerId = paramsDto.getOwnerId();
		Boolean approve = paramsDto.getApprove();

		String errorMessage = "Невозможно подтвердить или отклонить бронирование.";
		Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(bookingId, errorMessage));

		if (booking.getItem().getOwner().getId() != ownerId) {
			throw new ItemDoesNotBelongToTheOwnerException(booking.getItem().getId(), paramsDto.getOwnerId(),
					errorMessage);
		}
		
//		if (booking.getStatus().equals(BookingStatus.APPROVED)) {
//			throw new BookingAllReadyApproved(bookingId, errorMessage);
//		}

		BookingStatus status = approve ? BookingStatus.APPROVED : BookingStatus.REJECTED;
		booking.setStatus(status);
		
		Booking createdBooking = bookingRepository.save(booking);

		return BookingMapper.bookingToResponseBookingDto(createdBooking);
	}


	public List<ResponseBookingDto> getAllBookingsOfUser(@Positive @NotNull Long ownerId) {
		String errorMessage = "Невозможно получить список бронирований пользователя.";
		User user = getUser(ownerId, errorMessage);
		return user.getBookings().stream()
				.map(BookingMapper::bookingToResponseBookingDto)
				.toList();
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

	public ResponseBookingDto getAllBookingsOfOwner(ParamsDto paramsDto) {
		String errorMessage = "Невозможно получить список бронирований владельца.";

		Long bookingId = paramsDto.getBookingId();
		Booking booking = getBooking(bookingId, errorMessage);

		return BookingMapper.bookingToResponseBookingDto(booking);
	}
}
