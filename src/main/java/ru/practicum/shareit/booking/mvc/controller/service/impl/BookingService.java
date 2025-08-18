package ru.practicum.shareit.booking.mvc.controller.service.impl;

import org.springframework.stereotype.Service;

import ru.practicum.shareit.booking.exception.BookingException;
import ru.practicum.shareit.booking.exception.BookingNotFoundException;
import ru.practicum.shareit.booking.mvc.controller.repository.BookingRepositoryApp;
import ru.practicum.shareit.booking.mvc.controller.service.BookingServiceApp;
import ru.practicum.shareit.booking.mvc.model.Booking;
import ru.practicum.shareit.booking.mvc.model.dto.CreateBookingDto;
import ru.practicum.shareit.booking.mvc.model.dto.ResponseBookingDto;
import ru.practicum.shareit.booking.mvc.model.dto.UpdateBookingDto;
import ru.practicum.shareit.booking.utills.BookingMapper;
import ru.practicum.shareit.item.exception.ItemDoesNotBelongToTheOwnerException;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.mvc.controller.repository.ItemRepositoryApp;
import ru.practicum.shareit.item.mvc.model.Item;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.mvc.controller.repository.UserRepositoryApp;
import ru.practicum.shareit.user.mvc.model.User;

@Service
public class BookingService implements BookingServiceApp {

	private final BookingRepositoryApp bookingRepository;
	private final ItemRepositoryApp itemRepository;
	private final UserRepositoryApp userRepository;
	private final BookingException bookingException;

	public BookingService(BookingRepositoryApp bookingRepository, ItemRepositoryApp itemRepository,
			UserRepositoryApp userRepository, BookingException bookingException) {
		this.bookingRepository = bookingRepository;
		this.itemRepository = itemRepository;
		this.userRepository = userRepository;
		this.bookingException = bookingException;
	}

	@Override
	public ResponseBookingDto createBooking(CreateBookingDto createBookingDto) {
		String errorMessage = "Невозможно создать бронирование";
		Booking booking = createBookingDtoToBooking(createBookingDto, errorMessage);

		Booking createdBooking = bookingRepository.save(booking);

		ResponseBookingDto responseBooking = BookingMapper.bookingToResponseBookingDto(createdBooking);

		return responseBooking;
	}

	@Override
	public ResponseBookingDto updateBooking(UpdateBookingDto updateBookingDto) {
		Long bookingId = updateBookingDto.getId();

		String errorMessage = "Невозможно обновить бронирование";
		bookingException.checkBookingNotFoundException(bookingId, errorMessage);

		Booking booking = updateBookingDtoToBooking(updateBookingDto, errorMessage);

		Booking updatedBooking = bookingRepository.save(booking);

		ResponseBookingDto responseBooking = BookingMapper.bookingToResponseBookingDto(updatedBooking);

		return responseBooking;
	}

	public ResponseBookingDto getBooking(Long bookingId) {
		String errorMessge = "Невозможно получить бронирование.";
		Booking responseBooking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(bookingId, errorMessge));
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
		Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId, errorMessage));
		booking.setItem(item);

		Long requestOwnerId = createBookingDto.getOwnerId();

		User requestOwner = userRepository.findById(requestOwnerId).orElseThrow(() -> new UserNotFoundException(requestOwnerId, errorMessage));

		if (requestOwner.getItems().contains(item)) {
			throw new ItemDoesNotBelongToTheOwnerException(itemId, requestOwnerId, errorMessage);
		}
		
		if (!booking.getItem().getAvailable()) {
			throw new ItemIsUnavailableException(booking.getItem(), errorMessage);
		}
		return booking;
	}

	private Booking updateBookingDtoToBooking(UpdateBookingDto updateBookingDto, String errorMessage) {
		Booking booking = BookingMapper.updateBookingDtoToBooking(updateBookingDto);

		Long itemId = updateBookingDto.getItemId();
		Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId, errorMessage));
		booking.setItem(item);

		Long bookerId = updateBookingDto.getBookerId();
		User user = userRepository.findById(bookerId).orElseThrow(() -> new UserNotFoundException(bookerId, errorMessage));
		booking.setBooker(user);

		return booking;
	}

}
