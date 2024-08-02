package ru.practicum.shareit.booking.storage;

import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingStorage {
    Booking addBooking(Booking booking);

    Booking updateBooking(Booking booking);

    Booking getBookingById(Long bookingId, Long userId);

    List<Booking> getBookingsByBooker(Long userId);

    List<Booking> getBookingsByItemOwner(Long ownerId);

    Booking getLastItemBooking(Long itemId);

    Booking getNextItemBooking(Long itemId);

    List<Booking> getBookingsByItem(Long itemId);

}
