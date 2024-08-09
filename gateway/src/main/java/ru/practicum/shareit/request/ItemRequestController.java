package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import static ru.practicum.shareit.constants.HeaderConstants.USER_ID_HEADER;

@Validated
@RequiredArgsConstructor
@Controller
@RequestMapping("/requests")
public class ItemRequestController {

    private final ItemRequestClient itemRequestClient;

    @PostMapping
    public ResponseEntity<Object> createRequest(@RequestBody @Valid ItemRequestDto itemRequestDto,
                                                @RequestHeader(USER_ID_HEADER) Long userId) {
        return itemRequestClient.createItemRequest(itemRequestDto, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getUsersRequests(@RequestHeader(USER_ID_HEADER) Long userId) {
        return itemRequestClient.getUsersRequests(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(@PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                                 @Positive @RequestParam(defaultValue = "10") int size) {
        return itemRequestClient.getAllRequests(from, size);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getItemRequest(@PathVariable Long requestId) {
        return itemRequestClient.getItemRequest(requestId);
    }
}
