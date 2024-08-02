package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.error.exception.UserOperationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingDtoMapper mapper;
    private final BookingStorage bookingStorage;
    private final UserStorage userStorage;
    private final ItemStorage itemStorage;

    public BookingDto createBooking(BookingDto dto, Long id) {
        Item item = itemStorage.getItem(dto.getItemId());
        User user = userStorage.getUser(id);
        dto.setBooker(user);
        if (!item.getAvailable()) {
            throw new UserOperationException("Вещь не доступна");
        }
        Booking booking = mapper.dtoToBooking(dto);
        booking.setBooker(userStorage.getUser(id));
        booking.setItem(item);
        booking.setStatus(BookingStatus.WAITING);
        return mapper.bookingToDto(bookingStorage.addBooking(booking));
    }

    public BookingDto updateBooking(Long bookingId, boolean approved, Long userId) {
        Booking booking = bookingStorage.getBookingById(bookingId, userId);
        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new UserOperationException("Доступ запрещен");
        }
        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        return mapper.bookingToDto(bookingStorage.updateBooking(booking));
    }

    public BookingDto getBooking(Long bookingId, Long userId) {
        return mapper.bookingToDto(bookingStorage.getBookingById(bookingId, userId));
    }

    public List<BookingDto> getUserBookings(Long userId, String state) {

        userStorage.getUser(userId);
        Stream<BookingDto> bookings = bookingStorage.getBookingsByBooker(userId).stream()
                .map(mapper::bookingToDto);
        return filterByState(bookings, state);
    }

    public List<BookingDto> getBookingsByOwner(Long userId, String state) {

        userStorage.getUser(userId);
        Stream<BookingDto> bookings = bookingStorage.getBookingsByItemOwner(userId).stream()
                        .map(mapper::bookingToDto);
        return filterByState(bookings, state);
    }

    private List<BookingDto> filterByState(Stream<BookingDto> bookings, String state) {
        switch (state) {
            case ("ALL"):
                return bookings.sorted(Comparator.comparing(BookingDto::getStart))
                        .toList();
            case ("CURRENT"):
                return bookings
                        .filter(booking -> booking.getStart().isBefore(LocalDateTime.now())
                                && booking.getEnd().isAfter(LocalDateTime.now()))
                        .sorted(Comparator.comparing(BookingDto::getStart))
                        .toList();
            case ("PAST"):
                return bookings
                        .filter(booking -> booking.getStart().isBefore(LocalDateTime.now())
                                && booking.getEnd().isBefore(LocalDateTime.now()))
                        .sorted(Comparator.comparing(BookingDto::getStart))
                        .toList();
            case ("FUTURE"):
                return bookings
                        .filter(booking -> booking.getStart().isAfter(LocalDateTime.now())
                                && booking.getEnd().isAfter(LocalDateTime.now()))
                        .sorted(Comparator.comparing(BookingDto::getStart))
                        .toList();
            case ("WAITING"):
                return bookings
                        .filter(booking -> booking.getStatus() == BookingStatus.WAITING)
                        .sorted(Comparator.comparing(BookingDto::getStart))
                        .toList();
            case ("REJECTED"):
                return bookings
                        .filter(booking -> booking.getStatus() == BookingStatus.REJECTED)
                        .sorted(Comparator.comparing(BookingDto::getStart))
                        .toList();
            default:
                throw new UserOperationException("Ошибка выбора");
        }
    }
}
