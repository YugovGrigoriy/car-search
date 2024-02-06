package ru.edu.entity;

import jakarta.persistence.*;



@Entity
@Table(name = "report")
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column
    private String LocalDateTime;
    @Column
    private String name;
    @Column
    private String message;
    @Column
    private String email;

    public ReportEntity() {
    }

    public ReportEntity(String localDateTime, String name, String message, String email) {
        LocalDateTime = localDateTime;
        this.name = name;
        this.message = message;
        this.email = email;
    }

    public String getLocalDateTime() {
        return LocalDateTime;
    }

    public void setLocalDateTime(String localDateTime) {
        LocalDateTime = localDateTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ReportEntity{" +
            "id=" + id +
            ", LocalDateTime=" + LocalDateTime +
            ", name='" + name + '\'' +
            ", message='" + message + '\'' +
            '}';
    }
}
