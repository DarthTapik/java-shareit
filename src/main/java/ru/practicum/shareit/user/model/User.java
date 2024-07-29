package ru.practicum.shareit.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email(message = "Неверный формат email")
    @NotBlank(message = "email не может быть пустым")
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;
}
