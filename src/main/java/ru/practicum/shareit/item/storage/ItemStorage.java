package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {
    Item getItem(Integer itemId);

    Item createItem(Item item);

    Item updateItem(Item item);

    List<Item> getAllUserItem(Integer ownerId);

    List<Item> searchItem(String text);
}
