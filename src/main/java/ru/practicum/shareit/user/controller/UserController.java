package ru.practicum.shareit.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    @PostMapping
    public UserDto createUser(@Valid @RequestBody UserDto dto) {
        return userService.addUser(dto);
    }

    @PatchMapping("/{id}")
    public UserDto updateUser(@RequestBody UserDto dto, @PathVariable Integer id) {
        dto.setId(id);
        return userService.updateUser(dto);
    }

    @DeleteMapping("/{id}")
    public UserDto deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }
}
