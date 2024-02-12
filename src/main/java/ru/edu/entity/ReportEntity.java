package ru.edu.entity;

import jakarta.persistence.*;
import lombok.*;


@Data
@Entity
@Table(name = "report")

@AllArgsConstructor
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
