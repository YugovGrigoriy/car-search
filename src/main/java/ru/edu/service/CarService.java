package ru.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edu.entity.CarEntity;
import ru.edu.repo.CarRepository;

import java.util.List;

@Service
public class CarService {

    CarRepository repository;

    public CarEntity findCar(String model, String vehicleGeneration){
        return repository.findByModelAndVehicleGeneration(model,vehicleGeneration);
    }
    public CarEntity findCar(String id){
        return repository.findCarById(Long.parseLong(id));
    }
    public List<CarEntity>findAllCar(){
        return repository.findByBrand("ford");
    }
    public CarEntity updatePriceCar(long idCar,int newPrice){
        CarEntity car=findCar(String.valueOf(idCar));
        car.setPrice(String.valueOf(newPrice));
        return  repository.save(car);
    }

    @Autowired
    public void setRepository(CarRepository repository) {
        this.repository = repository;
    }
}
