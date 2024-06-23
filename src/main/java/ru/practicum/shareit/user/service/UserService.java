package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.error.exception.ValueNotUniqueException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDtoMapper userMapper;
    private final UserStorage userStorage;

    public UserDto getUser(Integer id) {
        return userMapper.userToDto(userStorage.getUser(id));
    }

    public List<UserDto> getAll() {
        return userStorage.getAll().stream()
                .map(userMapper::userToDto)
                .collect(Collectors.toList());
    }

    public UserDto addUser(UserDto dto) {
        if (!uniqueEmail(dto.getEmail()).equals(-1)) {
            throw new ValueNotUniqueException("Данный email уже зарегестрирован");
        }
        return userMapper.userToDto(
                userStorage.addUser(userMapper.dtoToUser(dto))
        );
    }

    public UserDto updateUser(UserDto dto) {
        User updateUser = userStorage.getUser(dto.getId());
        if (dto.getName() != null) {
            updateUser.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            if (!uniqueEmail(dto.getEmail()).equals(dto.getId()) && !uniqueEmail(dto.getEmail()).equals(-1)) {
                throw new ValueNotUniqueException("Данный email уже зарегестрирован");
            }
            updateUser.setEmail(dto.getEmail());
        }
        return userMapper.userToDto(
                userStorage.updateUser(updateUser)
        );
    }

    public UserDto deleteUser(Integer id) {
        return userMapper.userToDto(userStorage.deleteUser(id));
    }

    private Integer uniqueEmail(String email) {

        Optional<User> userOptional = userStorage.getAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
        if (userOptional.isEmpty()) {
            return -1;
        }
        return userOptional.get().getId();
    }
}
