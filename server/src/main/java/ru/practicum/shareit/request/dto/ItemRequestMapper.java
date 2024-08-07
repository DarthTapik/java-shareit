package ru.practicum.shareit.request.dto;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.request.model.ItemRequest;

@Component
public class ItemRequestMapper {
    public ItemRequestDto itemRequestToDto(ItemRequest itemRequest) {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(itemRequest.getId());
        itemRequestDto.setDescription(itemRequest.getDescription());
        itemRequestDto.setRequestor(itemRequest.getRequestor());
        itemRequestDto.setCreated(itemRequest.getCreated());
        return itemRequestDto;
    }

    public ItemRequest dtoToItemRequest(ItemRequestDto itemRequestDto) {
        return new ItemRequest(itemRequestDto.getId(), itemRequestDto.getDescription(),
                itemRequestDto.getRequestor(), itemRequestDto.getCreated());
    }
}
