package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.validation.Create;
import ru.practicum.shareit.validation.Update;

@NoArgsConstructor
@Data
public class UserDto {
    private Long id;
    @Size(max = 255, groups = {Create.class, Update.class})
    @NotBlank(groups = {Create.class})
    private String name;
    @Size(max = 512, groups = {Create.class, Update.class})
    @Email(message = "Неверный формат email", groups = {Create.class, Update.class})
    @NotBlank(message = "email не может быть пустым", groups = {Create.class})
    private String email;
}