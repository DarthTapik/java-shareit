package ru.practicum.shareit.booking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingDto addBooking(@Valid @RequestBody BookingDto bookingDto, @RequestHeader("X-Sharer-User-Id") Long id) {
        return bookingService.createBooking(bookingDto, id);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto answerBooking(@PathVariable(name = "bookingId") Long bookingId,
                                    @RequestParam(name = "approved") Boolean approved,
                                    @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.updateBooking(bookingId, approved, userId);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@PathVariable(name = "bookingId") Long bookingId,
                                     @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.getBooking(bookingId, userId);
    }

    @GetMapping
    public List<BookingDto> getUserBookings(@RequestHeader("X-Sharer-User-Id") Long id,
                                            @RequestParam(name = "state", required = false,
                                                    defaultValue = "ALL") String state) {
        return bookingService.getUserBookings(id, state);
    }

    @GetMapping("owner")
    public List<BookingDto> getBookingsByOwner(@RequestHeader("X-Sharer-User-Id") Long id,
                                               @RequestParam(name = "state", required = false,
                                                       defaultValue = "ALL") String state) {
        return bookingService.getBookingsByOwner(id, state);
    }
}
