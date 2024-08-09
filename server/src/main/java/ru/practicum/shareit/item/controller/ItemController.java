package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemBookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

import static ru.practicum.shareit.constants.HeaderConstants.USER_ID_HEADER;

@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/{id}")
    public ItemBookingDto getItem(@PathVariable Long id) {
        return itemService.getItem(id);
    }

    @GetMapping
    public List<ItemBookingDto> getAllItem(@RequestHeader(USER_ID_HEADER) Long id) {
        return itemService.getAll(id);
    }

    @PostMapping
    public ItemDto addItem(@RequestBody ItemDto itemDto, @RequestHeader(USER_ID_HEADER) Long id) {
        return itemService.addItem(itemDto, id);
    }

    @PatchMapping("/{id}")
    public ItemDto updateItem(@RequestBody ItemDto itemDto, @RequestHeader(USER_ID_HEADER) Long ownerId,
                              @PathVariable("id") Long itemId) {
        return itemService.updateItem(itemDto, ownerId, itemId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItem(@RequestParam("text") String text) {
        return itemService.searchItem(text);
    }

    @PostMapping("/{id}/comment")
    public CommentDto addComment(@RequestBody Comment comment, @RequestHeader(USER_ID_HEADER) Long userId,
                                 @PathVariable("id") Long itemId) {
        return itemService.addComment(comment, itemId, userId);
    }
}
