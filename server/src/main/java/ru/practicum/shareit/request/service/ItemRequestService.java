package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestMapper;
import ru.practicum.shareit.request.storage.ItemRequestStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemRequestService {
    private final ItemRequestMapper itemRequestMapper;
    private final ItemRequestStorage itemRequestStorage;
    private final UserStorage userStorage;
    private final ItemStorage itemStorage;

    public ItemRequestDto createItemRequest(ItemRequestDto itemRequestDto, Long userId) {
        User requestor = userStorage.getUser(userId);
        itemRequestDto.setRequestor(requestor);
        itemRequestDto.setCreated(LocalDateTime.now());
        ItemRequest itemRequest = itemRequestMapper.dtoToItemRequest(itemRequestDto);
        return itemRequestMapper.itemRequestToDto(itemRequestStorage.addItemRequest(itemRequest));
    }

    public List<ItemRequestDto> getUsersRequests(Long userId) {
        userStorage.getUser(userId);
        List<ItemRequest> itemRequests = itemRequestStorage.getAllUsersRequests(userId);

        Map<ItemRequest, List<Item>> itemsByRequestId = itemStorage.getAllByRequests(itemRequests).stream()
                .collect(Collectors.groupingBy(Item::getItemRequest));
        List<ItemRequestDto> itemRequestDtos = itemRequests.stream()
                .map(itemRequestMapper::itemRequestToDto)
                .toList();

        itemRequestDtos.forEach(itemRequest ->
                itemRequest.setItems(itemsByRequestId.getOrDefault(itemRequestMapper.dtoToItemRequest(itemRequest),
                        Collections.emptyList()))
        );
        return itemRequestDtos;
    }

    public List<ItemRequestDto> getAllRequests(int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<ItemRequest> itemRequests = itemRequestStorage.getAll(pageable);
        Map<ItemRequest, List<Item>> itemsByRequestId = itemStorage.getAllByRequests(itemRequests).stream()
                .collect(Collectors.groupingBy(Item::getItemRequest));
        List<ItemRequestDto> itemRequestDtos = itemRequests.stream()
                .map(itemRequestMapper::itemRequestToDto)
                .toList();

        itemRequestDtos.forEach(itemRequest ->
                itemRequest.setItems(itemsByRequestId.getOrDefault(itemRequestMapper.dtoToItemRequest(itemRequest),
                        Collections.emptyList()))
        );
        return itemRequestDtos;
    }

    public ItemRequestDto getItemRequest(Long itemRequestId) {
        ItemRequestDto itemRequestDto = itemRequestMapper.itemRequestToDto(itemRequestStorage
                .getItemRequest(itemRequestId));
        itemRequestDto.setItems(itemStorage.getAllByRequestId(itemRequestId));
        return itemRequestDto;
    }
}
