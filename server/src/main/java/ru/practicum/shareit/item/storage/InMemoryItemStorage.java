package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class InMemoryItemStorage {
    private Long idCounter = 1L;
    private final Map<Long, Item> items = new HashMap<>();

    public Item getItem(Long itemId) {
        Item item = items.get(itemId);
        if (item == null) {
            throw new NotFoundException("Предмет с id " + itemId + "не найден");
        }
        return item;
    }

    public Item createItem(Item item) {
        item.setId(idCounter++);
        items.put(item.getId(), item);
        return item;
    }

    public Item updateItem(Item item) {
        items.put(item.getId(), item);
        return item;
    }

    public List<Item> getAllUserItem(Long ownerId) {
        return items.values().stream()
                .filter(item -> item.getOwner().getId().equals(ownerId))
                .collect(Collectors.toList());
    }

    public List<Item> searchItem(String text) {
        System.out.println(items.values());
        return items.values().stream()
                .filter(item -> (item.getName().toLowerCase().contains(text)
                        || item.getDescription().toLowerCase().contains(text))
                        && item.getAvailable().equals(true))
                .collect(Collectors.toList());
    }

}
