package ru.practicum.shareit.booking.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookerId(Long userId);

    @Query("SELECT b FROM bookings b " +
            "JOIN b.item i " +
            "WHERE i.owner.id = :ownerId")
    List<Booking> findByOwnerId(Long ownerId);

    List<Booking> findByItemId(Long itemId);
}
