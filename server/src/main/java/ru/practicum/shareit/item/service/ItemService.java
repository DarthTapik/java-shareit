package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.error.exception.UserOperationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemBookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.CommentDBStorage;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.request.storage.ItemRequestStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemStorage itemStorage;
    private final ItemDtoMapper itemDtoMapper;
    private final UserStorage userStorage;
    private final BookingStorage bookingStorage;
    private final CommentDBStorage commentDBStorage;
    private final ItemRequestStorage itemRequestStorage;

    public ItemBookingDto getItem(Long id) {
        return itemDtoMapper.itemToBookingDto(itemStorage.getItem(id));
    }

    public List<ItemBookingDto> getAll(Long ownerId) {
        return itemStorage.getAllUserItem(ownerId).stream()
                .map(itemDtoMapper::itemToBookingDto)
                .collect(Collectors.toList());
    }

    public ItemDto addItem(ItemDto itemDto, Long ownerId) {
        User user = userStorage.getUser(ownerId);
        Item item = itemDtoMapper.dtoToItem(itemDto);
        item.setOwner(user);
        if (itemDto.getRequestId() != null) {
            item.setItemRequest(itemRequestStorage.getItemRequestOrNull(itemDto.getRequestId()));
        }
        return itemDtoMapper.itemToDto(
                itemStorage.createItem(item)
        );
    }

    public ItemDto updateItem(ItemDto itemDto, Long ownerId, Long itemId) {
        Item item = itemStorage.getItem(itemId);
        if (!item.getOwner().getId().equals(ownerId)) {
            throw new NotFoundException("");
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }

        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }

        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        return itemDtoMapper.itemToDto(itemStorage.updateItem(item));
    }

    public List<ItemDto> searchItem(String text) {
        if (text.isBlank() || text.isEmpty()) {
            return new ArrayList<>();
        }
        return itemStorage.searchItem(text.toLowerCase()).stream()
                .map(itemDtoMapper::itemToDto)
                .collect(Collectors.toList());
    }

    public CommentDto addComment(Comment comment, Long itemId, Long userId) {
        User user = userStorage.getUser(userId);
        Item item = itemStorage.getItem(itemId);
        List<Booking> bookings = bookingStorage.getBookingsByItem(itemId)
                .stream()
                .filter(booking -> (booking.getBooker().getId().equals(userId) &&
                        booking.getStatus().equals(BookingStatus.APPROVED)
                        && booking.getEnd().isBefore(LocalDateTime.now())))
                .toList();
        if (bookings.isEmpty()) {
            throw new UserOperationException("Вы не бронировали данную вещь");
        } else {
            comment.setAuthor(user);
            comment.setItem(item);
            comment.setCreated(LocalDateTime.now());
            return itemDtoMapper.commentToDto(commentDBStorage.addComment(comment));
        }
    }
}
