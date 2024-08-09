package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.CommentDBStorage;


import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemDtoMapper {
    private final BookingStorage bookingStorage;
    private final CommentDBStorage commentDBStorage;

    public ItemDto itemToDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        itemDto.setItemRequest(item.getItemRequest());
        return itemDto;
    }

    public Item dtoToItem(ItemDto itemDto) {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable());
        return item;
    }

    public ItemBookingDto itemToBookingDto(Item item) {
        ItemBookingDto itemDto = new ItemBookingDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        itemDto.setItemRequest(item.getItemRequest());
        itemDto.setLastBooking(bookingStorage.getLastItemBooking(item.getId()));
        itemDto.setNextBooking(bookingStorage.getNextItemBooking(item.getId()));
        List<CommentDto> comments = commentDBStorage.getAllItemComment(item.getId())
                .stream()
                .map(this::commentToDto)
                .toList();
        itemDto.setComments(comments);
        return itemDto;
    }

    public CommentDto commentToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setAuthorName(comment.getAuthor().getName());
        commentDto.setText(comment.getText());
        commentDto.setCreated(comment.getCreated());
        return commentDto;
    }
}
