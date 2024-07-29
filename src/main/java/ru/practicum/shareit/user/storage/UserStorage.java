package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserStorage {
    User getUser(Long id);

    List<User> getAll();

    User addUser(User user);

    User updateUser(User user);

    User deleteUser(Long id);
}
