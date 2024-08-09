package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.validation.Create;
import ru.practicum.shareit.validation.Update;

@Getter
@Setter
public class ItemDto {
    private Long id;
    @Size(max = 255, groups = {Create.class, Update.class})
    @NotBlank(groups = {Create.class})
    private String name;
    @Size(max = 512, groups = {Create.class, Update.class})
    @NotBlank(groups = {Create.class})
    private String description;
    @NotNull(groups = {Create.class})
    private Boolean available;
    private Long requestId;
}