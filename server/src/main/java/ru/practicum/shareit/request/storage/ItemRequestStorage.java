package ru.practicum.shareit.request.storage;

import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

public interface ItemRequestStorage {
    ItemRequest addItemRequest(ItemRequest itemRequest);

    List<ItemRequest> getAllUsersRequests(Long userId);

    List<ItemRequest> getAll(Pageable pageable);

    ItemRequest getItemRequest(Long itemRequestId);

    ItemRequest getItemRequestOrNull(Long itemRequestId);
}
