package ru.edu.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ford")
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
    private String pictureNumber;
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVehicleGeneration() {
        return vehicleGeneration;
    }

    public void setVehicleGeneration(String vehicleGeneration) {
        this.vehicleGeneration = vehicleGeneration;
    }

    public String getPictureNumber() {
        return pictureNumber;
    }

    public void setPictureNumber(String picture_number) {
        this.pictureNumber = picture_number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(String engine_capacity) {
        this.engineCapacity = engine_capacity;
    }

    public String getMaximumSpeed() {
        return maximumSpeed;
    }

    public void setMaximumSpeed(String maximum_speed) {
        this.maximumSpeed = maximum_speed;
    }

    public String getFullMass() {
        return fullMass;
    }

    public void setFullMass(String full_mass) {
        this.fullMass = full_mass;
    }

    public String getCarClass() {
        return carClass;
    }

    public void setCarClass(String car_class) {
        this.carClass = car_class;
    }

    @Override
    public String toString() {
        return "CarEntity{" +
            "id=" + id +
            ", brand='" + brand + '\'' +
            ", model='" + model + '\'' +
            ", vehicleGeneration='" + vehicleGeneration + '\'' +
            ", pictureNumber='" + pictureNumber + '\'' +
            ", price='" + price + '\'' +
            ", power='" + power + '\'' +
            ", engineCapacity='" + engineCapacity + '\'' +
            ", maximumSpeed='" + maximumSpeed + '\'' +
            ", fullMass='" + fullMass + '\'' +
            ", carClass='" + carClass + '\'' +
            '}';
    }
}
