package ru.edu.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "registered-users")
public class UserSite {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String name="default name";

    @Column(nullable = false)
    private int age=0;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "USER";
    @Column
    private String favoriteCar1;
    @Column
    private String favoriteCar2;
    @ManyToMany
    @CollectionTable(name = "favorite_cars", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "car")
    private List<CarEntity> favoriteCars = new ArrayList<>();

    public List<CarEntity> getFavoriteCars() {
        return favoriteCars;
    }

    public void setFavoriteCars(List<CarEntity> favoriteCars) {
        this.favoriteCars = favoriteCars;
    }

    public String getFavoriteCar2() {
        return favoriteCar2;
    }

    public void setFavoriteCar2(String favoriteCar2) {
        this.favoriteCar2 = favoriteCar2;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFavoriteCar1() {
        return favoriteCar1;
    }

    public void setFavoriteCar1(String favoriteCar) {
        this.favoriteCar1 = favoriteCar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserSite{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", name='" + name + '\'' +
            ", age=" + age +
            ", role='" + role + '\'' +
            '}';
    }
}
