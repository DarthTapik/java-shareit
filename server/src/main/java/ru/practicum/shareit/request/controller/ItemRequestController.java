package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

import static ru.practicum.shareit.constants.HeaderConstants.USER_ID_HEADER;


@RequiredArgsConstructor
@RestController
@RequestMapping("/requests")
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto createRequest(@RequestBody ItemRequestDto itemRequestDto,
                                        @RequestHeader(USER_ID_HEADER) Long userId) {
        return itemRequestService.createItemRequest(itemRequestDto, userId);
    }

    @GetMapping
    public List<ItemRequestDto> getUsersRequests(@RequestHeader(USER_ID_HEADER) Long userId) {
        return itemRequestService.getUsersRequests(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAllRequests(@RequestParam(defaultValue = "0") int from,
                                               @RequestParam(defaultValue = "10") int size) {
        return itemRequestService.getAllRequests(from, size);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getItemRequest(@PathVariable Long requestId) {
        return itemRequestService.getItemRequest(requestId);
    }
}
