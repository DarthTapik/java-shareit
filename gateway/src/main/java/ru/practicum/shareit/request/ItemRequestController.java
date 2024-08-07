package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/requests")
public class ItemRequestController {

    private final ItemRequestClient itemRequestClient;

    @PostMapping
    public ResponseEntity<Object> createRequest(@RequestBody @Valid ItemRequestDto itemRequestDto,
                                                @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestClient.createItemRequest(itemRequestDto, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getUsersRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestClient.getUsersRequests(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(@RequestParam(required = false, defaultValue = "0") int from,
                                                 @RequestParam(required = false, defaultValue = "10") int size) {
        return itemRequestClient.getAllRequests(from, size);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getItemRequest(@PathVariable Long requestId) {
        return itemRequestClient.getItemRequest(requestId);
    }
}
