package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {
    Item getItem(Long itemId);

    Item createItem(Item item);

    Item updateItem(Item item);

    List<Item> getAllUserItem(Long ownerId);

    List<Item> searchItem(String text);
}
