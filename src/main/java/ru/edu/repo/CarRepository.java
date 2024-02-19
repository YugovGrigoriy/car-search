package ru.edu.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.entity.CarEntity;

import java.util.List;


@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {
    CarEntity findByModelAndVehicleGeneration(String model, String vehicle_generation);

    List<CarEntity> findAll();

    CarEntity findCarById(Long id);
}
