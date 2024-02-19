package ru.edu.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "registered-users")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String name = "default name";

    @Column(nullable = false)
    private int age = 0;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "USER";
    @ManyToMany
    @CollectionTable(name = "favorite_cars", joinColumns = @JoinColumn(name = "user_id"))
    private List<CarEntity> favoriteCars = new ArrayList<>();
}
