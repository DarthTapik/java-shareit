package ru.practicum.shareit.error.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.error.exception.NotFoundException;
import ru.practicum.shareit.error.exception.UserOperationException;
import ru.practicum.shareit.error.exception.ValueNotUniqueException;
import ru.practicum.shareit.error.model.ErrorResponse;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        log.warn(e.getMessage());
        return new ErrorResponse("Не найдено",
                e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException e) {
        log.warn(e.getMessage());
        return new ErrorResponse("Ошибка валидации",
                e.getMessage());
    }

    @ExceptionHandler(UserOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserOperationException(UserOperationException e) {
        log.warn(e.getMessage());
        return new ErrorResponse("Ошибка выбора пользователя",
                e.getMessage());
    }

    @ExceptionHandler(ValueNotUniqueException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleValueNotUniqueException(ValueNotUniqueException e) {
        log.warn(e.getMessage());
        return new ErrorResponse("Ошибка уникальных данных",
                e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleOtherException(Exception e) {
        log.warn(e.getMessage());
        return new ErrorResponse("Ошибка сервера",
                e.getMessage());
    }
}
