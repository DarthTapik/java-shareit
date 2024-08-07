package ru.practicum.shareit.request.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRequestDBStorage implements ItemRequestStorage {
    private final ItemRequestRepository itemRequestRepository;

    public ItemRequest addItemRequest(ItemRequest itemRequest) {
        return itemRequestRepository.save(itemRequest);
    }

    public List<ItemRequest> getAllUsersRequests(Long userId) {
        return itemRequestRepository.findByRequestorId(userId);
    }

    public List<ItemRequest> getAll(Pageable pageable) {
        return itemRequestRepository.findAllByOrderByCreatedDesc(pageable);
    }

    public ItemRequest getItemRequest(Long itemRequestId) {
        return itemRequestRepository.findById(itemRequestId).orElseThrow(
                () -> new NotFoundException("Запрос вещи с id " + itemRequestId + " не найден")
        );
    }

    public ItemRequest getItemRequestOrNull(Long itemRequestId) {
        return itemRequestRepository.findById(itemRequestId).orElse(
                null
        );
    }
}
