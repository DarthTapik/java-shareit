package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemClient itemClient;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItem(@PathVariable Long id) {
        return itemClient.getItem(id);
    }

    @GetMapping
    public ResponseEntity<Object> getAllItem(@RequestHeader("X-Sharer-User-Id") Long id) {
        return itemClient.getAll(id);
    }

    @PostMapping
    public ResponseEntity<Object> addItem(@RequestBody @Valid ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Long id) {
        return itemClient.addItem(itemDto, id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateItem(@RequestBody ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") Long ownerId,
                              @PathVariable("id") Long itemId) {
        return itemClient.updateItem(itemDto, ownerId, itemId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItem(@RequestParam("text") String text) {
        return itemClient.searchItem(text);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Object> addComment(@RequestBody CommentDto comment,
                                             @RequestHeader("X-Sharer-User-Id") Long userId,
                                             @PathVariable("id") Long itemId) {
        return itemClient.addComment(comment, itemId, userId);
    }
}
