package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String name;
    @Email(message = "Неверный формат email")
    @NotBlank(message = "email не может быть пустым")
    private String email;
}
