package ru.practicum.shareit.item.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemDBStorage implements ItemStorage {

    private final ItemRepository itemRepository;

    @Override
    public Item getItem(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(
                () -> new NotFoundException("Предмет с id " + itemId + "не найден")
        );
    }

    @Override
    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public List<Item> getAllUserItem(Long ownerId) {
        return itemRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Item> searchItem(String text) {
        return itemRepository.search(text);
    }

    public List<Item> getAllByRequestId(Long requestId) {
        return itemRepository.findByItemRequestId(requestId);
    }

    @Override
    public List<Item> getAllByRequests(List<ItemRequest> itemRequests) {
        return itemRepository.findByItemRequestIn(itemRequests);
    }

}
