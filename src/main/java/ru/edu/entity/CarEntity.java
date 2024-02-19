package ru.edu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "ford")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column
    private String brand;
    @Column
    private String model;
    @Column
    private String vehicleGeneration;
    @Column
    private String price;
    @Column
    private String power;
    @Column
    private String engineCapacity;
    @Column
    private String maximumSpeed;
    @Column
    private String fullMass;
    @Column
    private String carClass;

    /**
     * Checks if the car entity is empty by checking if all fields are null.
     *
     * @return true if all fields are null, false otherwise.
     */
    public boolean carIsEmpty(){
        return this.brand == null && this.model == null && this.vehicleGeneration == null
            && this.price == null && this.power == null && this.engineCapacity == null &&
            this.maximumSpeed == null && this.fullMass == null && this.carClass == null;
    }

}
