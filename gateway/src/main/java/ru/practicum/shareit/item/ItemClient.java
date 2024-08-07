package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> getItem(Long id) {
        return get("/" + id);
    }

    public ResponseEntity<Object> getAll(Long userId) {
        return get("", userId);
    }


    public ResponseEntity<Object> addItem(ItemDto itemDto, Long id) {
        return post("", id, itemDto);
    }

    public ResponseEntity<Object> updateItem(ItemDto itemDto, Long ownerId, Long itemId) {
        return patch("/" + itemId, ownerId, itemDto);
    }

    public ResponseEntity<Object> searchItem(String text) {
        Map<String, Object> parameters = Map.of(
                "text", text
        );
        return get("/search?text={text}", null, parameters);
    }

    public ResponseEntity<Object> addComment(CommentDto comment, Long itemId, Long userId) {
        return post("/" + itemId + "/comment", userId, comment);
    }
}
