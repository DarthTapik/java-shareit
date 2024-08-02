package ru.practicum.shareit.booking.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.error.exception.UserOperationException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookingDBStorage implements BookingStorage {
    private final BookingRepository bookingRepository;

    @Override
    public Booking addBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBookingById(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new NotFoundException("Бронирование с id " + bookingId + " не найдено")
        );
        if (booking.getBooker().getId().equals(userId) || booking.getItem().getOwner().getId().equals(userId)) {
            return booking;
        } else {
            throw new UserOperationException("Доступ запрещен");
        }
    }

    @Override
    public List<Booking> getBookingsByBooker(Long userId) {
        return bookingRepository.findByBookerId(userId);
    }

    @Override
    public List<Booking> getBookingsByItemOwner(Long ownerId) {
        return bookingRepository.findByOwnerId(ownerId);
    }

    public List<Booking> getBookingsByItem(Long itemId) {
        return bookingRepository.findByItemId(itemId);
    }

    public Booking getLastItemBooking(Long itemId) {
        Optional<Booking> optionalBooking = getBookingsByItem(itemId).stream()
                .filter(booking -> (booking.getStart().isBefore(LocalDateTime.now())
                        && booking.getEnd().isBefore(LocalDateTime.now()))
                        && booking.getStatus().equals(BookingStatus.APPROVED))
                .max(Comparator.comparing(Booking::getEnd));
        if (optionalBooking.isEmpty()) {
            return null;
        }
        return optionalBooking.get();
    }

    public Booking getNextItemBooking(Long itemId) {
        Optional<Booking> optionalBooking = getBookingsByItem(itemId).stream()
                .filter(booking -> (booking.getStart().isAfter(LocalDateTime.now())
                        && booking.getEnd().isAfter(LocalDateTime.now()))
                        && booking.getStatus().equals(BookingStatus.APPROVED))
                .min(Comparator.comparing(Booking::getStart));
        if (optionalBooking.isEmpty()) {
            return null;
        }
        return optionalBooking.get();
    }
}
