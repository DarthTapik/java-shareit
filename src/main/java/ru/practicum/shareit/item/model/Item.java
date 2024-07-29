package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

@Data
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    @NotBlank
    private String name;
    @NotBlank
    @Column(name = "description")
    private String description;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @NotNull
    @Column(name = "available")
    private Boolean available;
}
