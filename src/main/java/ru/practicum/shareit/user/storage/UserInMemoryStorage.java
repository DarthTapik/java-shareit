package ru.practicum.shareit.user.storage;

import jakarta.validation.Valid;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class UserInMemoryStorage implements UserStorage {
    private final HashMap<Long, User> users = new HashMap<>();
    private Long idCounter = 1L;

    @Override
    public User getUser(Long id) {
        return Optional.ofNullable(users.get(id)).orElseThrow(
                () -> new NotFoundException("Пользователь с id " + id + "не найден")
        );
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User addUser(User user) {
        user.setId(idCounter);
        users.put(idCounter++, user);
        return user;
    }

    @Override
    public User updateUser(@Valid User user) {
        return users.put(user.getId(), user);
    }

    @Override
    public User deleteUser(Long id) {
        User deletedUser = users.remove(id);
        if (deletedUser == null) {
            throw new NotFoundException("Пользователь с id " + id + "не найден");
        }
        return deletedUser;
    }
}
