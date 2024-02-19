package ru.edu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edu.entity.CarEntity;
import ru.edu.repo.CarRepository;

import java.util.List;
/**
 * The CarService class provides service methods for interacting with car entities.
 */
@Service
public class CarService {

    private CarRepository repository;


    public CarService() {
    }

    /**
     * Constructor for CarService that autowires the CarRepository.
     *
     * @param repository The CarRepository to be used by the service.
     */
    @Autowired
    public CarService(CarRepository repository) {
        this.repository = repository;

    }
    /**
     * Finds a car by model and vehicle generation.
     *
     * @param model The model of the car.
     * @param vehicleGeneration The generation of the car.
     * @return The CarEntity matching the model and vehicle generation.
     */
    public CarEntity findCar(String model, String vehicleGeneration) {
        return repository.findByModelAndVehicleGeneration(model, vehicleGeneration);
    }
    /**
     * Finds a car by its ID.
     *
     * @param id The ID of the car.
     * @return The CarEntity with the specified ID.
     */
    public CarEntity findCar(String id) {
        return repository.findCarById(Long.parseLong(id));
    }
    /**
     * Retrieves a list of all cars.
     *
     * @return A list of all CarEntity objects.
     */
    public List<CarEntity> findAllCar() {
        return repository.findAll();
    }

    /**
     * Updates the price of a car.
     *
     * @param idCar The ID of the car to update.
     * @param newPrice The new price to set for the car.
     */
    public void updatePriceCar(long idCar, int newPrice) {
        CarEntity car = findCar(String.valueOf(idCar));
        car.setPrice(String.valueOf(newPrice));
        repository.save(car);
    }


}
