package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/requests")
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto createRequest(@RequestBody ItemRequestDto itemRequestDto,
                                        @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestService.createItemRequest(itemRequestDto, userId);
    }

    @GetMapping
    public List<ItemRequestDto> getUsersRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestService.getUsersRequests(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAllRequests(@RequestParam(required = false, defaultValue = "0") int from,
                                               @RequestParam(required = false, defaultValue = "10") int size) {
        return itemRequestService.getAllRequests(from, size);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getItemRequest(@PathVariable Long requestId) {
        return itemRequestService.getItemRequest(requestId);
    }
}
