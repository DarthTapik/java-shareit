package ru.practicum.shareit.error.exception;

public class UserOperationException extends RuntimeException {
    public UserOperationException(String message) {
        super(message);
    }
}
