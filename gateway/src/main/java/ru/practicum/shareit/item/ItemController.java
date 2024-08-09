package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.validation.Create;
import ru.practicum.shareit.validation.Update;

import static ru.practicum.shareit.constants.HeaderConstants.USER_ID_HEADER;

@RequiredArgsConstructor
@Controller
@RequestMapping("/items")
public class ItemController {
    private final ItemClient itemClient;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItem(@PathVariable Long id) {
        return itemClient.getItem(id);
    }

    @GetMapping
    public ResponseEntity<Object> getAllItem(@RequestHeader(USER_ID_HEADER) Long id) {
        return itemClient.getAll(id);
    }

    @PostMapping
    public ResponseEntity<Object> addItem(@RequestBody @Validated(Create.class) ItemDto itemDto, @RequestHeader(USER_ID_HEADER) Long id) {
        return itemClient.addItem(itemDto, id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateItem(@RequestBody @Validated({Update.class}) ItemDto itemDto, @RequestHeader(USER_ID_HEADER) Long ownerId,
                                             @PathVariable("id") Long itemId) {
        return itemClient.updateItem(itemDto, ownerId, itemId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItem(@RequestParam("text") String text) {
        if (text.isBlank()) {
            return ResponseEntity.accepted().body("");
        }
        return itemClient.searchItem(text);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Object> addComment(@RequestBody @Valid CommentDto comment,
                                             @RequestHeader(USER_ID_HEADER) Long userId,
                                             @PathVariable("id") Long itemId) {
        return itemClient.addComment(comment, itemId, userId);
    }
}
