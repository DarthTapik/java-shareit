package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    @Email(message = "Неверный формат email")
    @NotBlank(message = "email не может быть пустым")
    private String email;
    private String name;
}
