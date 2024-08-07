package ru.practicum.shareit.booking;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.booking.dto.BookingDto;


@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> addBooking(@RequestBody BookingDto bookingDto, @RequestHeader("X-Sharer-User-Id") Long id) {
        return bookingClient.createBooking(bookingDto, id);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> answerBooking(@PathVariable(name = "bookingId") Long bookingId,
                                                @RequestParam(name = "approved") Boolean approved,
                                                @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingClient.updateBooking(bookingId, approved, userId);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBookingById(@PathVariable(name = "bookingId") Long bookingId,
                                                 @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingClient.getBooking(bookingId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getUserBookings(@RequestHeader("X-Sharer-User-Id") Long id,
                                                  @RequestParam(name = "state", required = false,
                                                          defaultValue = "ALL") String state) {
        return bookingClient.getUserBookings(id, state);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getBookingsByOwner(@RequestHeader("X-Sharer-User-Id") Long id,
                                                     @RequestParam(name = "state", required = false,
                                                             defaultValue = "ALL") String state) {
        return bookingClient.getBookingsByOwner(id, state);
    }
}
