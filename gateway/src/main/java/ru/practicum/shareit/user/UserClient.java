package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.user.dto.UserDto;

@Service
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/users";

    @Autowired
    public UserClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> getAll(){
        return super.get("");
    }

    public ResponseEntity<Object> getUser(Long id){
        return super.get("/" + id);
    }

    public ResponseEntity<Object> createUser(UserDto dto){
        return super.post("", dto);
    }

    public ResponseEntity<Object> updateUser(UserDto dto){
        return super.patch("/" + dto.getId(), dto);
    }

    public ResponseEntity<Object> deleteUser(Long id){
        return super.delete("/" + id);
    }


}
