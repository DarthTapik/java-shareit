package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemStorage itemStorage;
    private final ItemDtoMapper itemDtoMapper;
    private final UserStorage userStorage;

    public ItemDto getItem(Integer id) {
        return itemDtoMapper.itemToDto(itemStorage.getItem(id));
    }

    public List<ItemDto> getAll(Integer ownerId) {
        return itemStorage.getAllUserItem(ownerId).stream()
                .map(itemDtoMapper::itemToDto)
                .collect(Collectors.toList());
    }

    public ItemDto addItem(ItemDto itemDto, Integer ownerId) {
        userStorage.getUser(ownerId);
        Item item = itemDtoMapper.dtoToItem(itemDto);
        item.setOwnerId(ownerId);
        return itemDtoMapper.itemToDto(
                itemStorage.createItem(item)
        );
    }

    public ItemDto updateItem(ItemDto itemDto, Integer ownerId, Integer itemId) {
        Item item = itemStorage.getItem(itemId);
        if (!item.getOwnerId().equals(ownerId)) {
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
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return itemStorage.searchItem(text.toLowerCase()).stream()
                .map(itemDtoMapper::itemToDto)
                .collect(Collectors.toList());
    }
}
