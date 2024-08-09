package ru.practicum.shareit.item.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByOwnerId(Long ownerId);

    @Query("SELECT i FROM Item i " +
            "WHERE (i.name ILIKE %:text% " +
            "OR i.description ILIKE %:text%) " +
            "AND i.available = true")
    List<Item> search(String text);

    List<Item> findByItemRequestId(Long requestId);

    List<Item> findByItemRequestIn(List<ItemRequest> itemRequests);

}
