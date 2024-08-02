package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.storage.BookingDBStorage;
import ru.practicum.shareit.item.model.Item;

@Component
@RequiredArgsConstructor
public class ItemDtoMapper {
    private final BookingDBStorage bookingDBStorage;

    public ItemDto itemToDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
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
        itemDto.setLastBooking(bookingDBStorage.getLastItemBooking(item.getId()));
        itemDto.setNextBooking(bookingDBStorage.getNextItemBooking(item.getId()));
        return itemDto;
    }
}
