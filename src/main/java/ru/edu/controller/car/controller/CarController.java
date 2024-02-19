package ru.edu.controller.car.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edu.controller.user.controller.ApiUserController;
import ru.edu.entity.CarEntity;
import ru.edu.entity.UserEntity;
import ru.edu.service.CarService;
import ru.edu.service.UserService;
import ru.edu.utils.CompareCar;
import ru.edu.utils.StringFormatter;

import static ru.edu.utils.ToRomanNumerals.toRoman;
/**
 * The CarController class handles requests related to car operations and functionalities.
 * It manages tasks such as finding cars, comparing cars, and displaying car details on the UI.
 *
 * @Controller annotation indicates that this class serves the role of a controller in Spring MVC.
 * @RequestMapping (value = "/car") specifies the base URL path for all methods in this controller.
 */
@Controller
@RequestMapping(value = "/car")
public class CarController {
    private UserService userService;
    private CarService carService;
    private static final Logger logger = LoggerFactory.getLogger(ApiUserController.class);

    public CarController() {
    }
    /**
     * Constructor with dependencies injection for CarController.
     *
     * @param userService The UserService instance to handle user-related operations.
     * @param carService  The CarService instance to handle car-related operations.
     */
    @Autowired
    public CarController(UserService userService, CarService carService) {
        this.userService = userService;
        this.carService = carService;
    }
    /**
     * API endpoint to find a car based on the model and vehicle generation.
     *
     * @param model            The model attribute for the car.
     * @param vehicleGeneration The vehicle generation attribute for the car.
     * @return The view name for redirecting to the main page.
     */
    @GetMapping("/find")
    public String findCar(Model model,
                          @RequestParam("model") String carModel,
                          @RequestParam("vehicleGeneration") String vehicleGeneration) {
        if (carModel.isEmpty() || vehicleGeneration.isEmpty()) {
            model.addAttribute("error", "Error: Car model and generation are required");
            return "engine";
        }
        String newCarModel = carModel.substring(carModel.indexOf(" ") + 1).toLowerCase();
        CarEntity car = carService.findCar(newCarModel, vehicleGeneration);
        if (car == null) {
            model.addAttribute("error", String.format("Error: \"%s %s \" nothing was found, perhaps you were mistaken or we have not yet managed to add this car",
                carModel,
                vehicleGeneration));
            return "engine";
        }
        String brand = StringFormatter.capitalizeFirstLetter(car.getBrand());

        carModel = StringFormatter.capitalizeFirstLetter(car.getModel());
        String newVehicleGeneration = toRoman(Integer.parseInt(car.getVehicleGeneration()));
        String price = "Price: " + car.getPrice() + "$";
        String power = "Car power: " + car.getPower() + " hp";
        String engineCapacity = "Engine capacity: " + car.getEngineCapacity() + "L";
        String maximumSpeed = "Max speed: " + car.getMaximumSpeed() + " km/h";
        String fullMass = "Full mass: " + car.getFullMass() + " kg";
        String carClass = "Class car: " + car.getCarClass();
        model.addAttribute("idCar", car.getId());
        model.addAttribute("model", brand + " " + carModel + " " + newVehicleGeneration);
        model.addAttribute("price", price);
        model.addAttribute("power", power);
        model.addAttribute("engineCapacity", engineCapacity);
        model.addAttribute("maximumSpeed", maximumSpeed);
        model.addAttribute("fullMass", fullMass);
        model.addAttribute("carClass", carClass);
        model.addAttribute("img", String.format("../image/car_picture/%s%s%s.png",
            car.getBrand(),
            car.getModel(),
            car.getVehicleGeneration()));
        model.addAttribute("imgLogo", String.format("../image/car_logo/%s.png",
            car.getBrand()));
        return "engine";
    }
    /**
     * API endpoint to compare two cars and display the comparison result.
     *
     * @param carForComparison1 The description of the first car for comparison.
     * @param carForComparison2 The description of the second car for comparison.
     * @param model             The model to add attributes for rendering the view.
     * @return The view name for redirecting to the personal area page.
     */
    @GetMapping(value = "/compare")
    public String compare(@RequestParam("position1") String carForComparison1,
                          @RequestParam("position2") String carForComparison2,
                          Model model) {
        if (carForComparison1.isEmpty() || carForComparison2.isEmpty()) {
            return "personal-area";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userService.findByUsername(username);
        if (user == null) {
            logger.error("User: " + username + " not found");
            return "personal-area";
        }
        String idCar1 = StringFormatter.extractIdFromDescription(carForComparison1);
        String idCar2 = StringFormatter.extractIdFromDescription(carForComparison2);
        CarEntity car1 = carService.findCar(idCar1);
        CarEntity car2 = carService.findCar(idCar2);
        if(car1==null||car2==null){
            logger.error("Failed to add favorite car: "+car1+" "+car2);
            return "personal-area";
        }
        model.addAttribute("name", user.getName());
        model.addAttribute("age", user.getAge());
        model.addAttribute("resultCompareCar", CompareCar.buildCompareResult(car1, car2));
        return "personal-area";

}


}
