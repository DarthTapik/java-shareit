package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestMapper;
import ru.practicum.shareit.request.storage.ItemRequestStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.time.LocalDateTime;
import java.util.List;

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
        User requestor = userStorage.getUser(userId);
        List<ItemRequestDto> itemRequests = itemRequestStorage.getAllUsersRequests(userId).stream()
                .map(itemRequestMapper::itemRequestToDto)
                .toList();
        itemRequests
                .forEach(
                        itemRequestDto ->
                                itemRequestDto.setItems(itemStorage.getAllByRequestId(itemRequestDto.getId()))
                );
        return itemRequests;
    }

    public List<ItemRequestDto> getAllRequests(int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<ItemRequestDto> itemRequestsDto = itemRequestStorage.getAll(pageable).stream()
                .map(itemRequestMapper::itemRequestToDto)
                .toList();
        itemRequestsDto.
                forEach(itemRequestDto ->
                        itemRequestDto.setItems(itemStorage.getAllByRequestId(itemRequestDto.getId()))
                );
        return itemRequestsDto;
    }

    public ItemRequestDto getItemRequest(Long itemRequestId) {
        ItemRequestDto itemRequestDto = itemRequestMapper.itemRequestToDto(itemRequestStorage
                .getItemRequest(itemRequestId));
        itemRequestDto.setItems(itemStorage.getAllByRequestId(itemRequestId));
        return itemRequestDto;
    }
}
