package ru.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edu.entity.CarEntity;
import ru.edu.repo.CarRepository;

@Service
public class CarService {

    CarRepository repository;

    public CarEntity findCar(String model, String vehicleGeneration){
        return repository.findByModelAndVehicleGeneration(model,vehicleGeneration);
    }

    @Autowired
    public void setRepository(CarRepository repository) {
        this.repository = repository;
    }
}
