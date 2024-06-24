package ru.practicum.shareit.user.storage;

import jakarta.validation.Valid;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class UserInMemoryStorage implements UserStorage {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int idCounter = 1;

    @Override
    public User getUser(Integer id) {
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
    public User deleteUser(Integer id) {
        User deletedUser = users.remove(id);
        if (deletedUser == null) {
            throw new NotFoundException("Пользователь с id " + id + "не найден");
        }
        return deletedUser;
    }
}
