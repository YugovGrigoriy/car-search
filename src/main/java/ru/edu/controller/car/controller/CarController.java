package ru.edu.controller.car.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edu.entity.CarEntity;
import ru.edu.entity.UserEntity;
import ru.edu.service.CarService;
import ru.edu.service.UserService;
import ru.edu.utils.CompareCar;
import ru.edu.utils.StringFormatter;


import static ru.edu.utils.ToRomanNumerals.toRoman;

@Controller
@RequestMapping(value = "/car")
public class CarController {
    private UserService userService;
    private CarService carService;

    public CarController() {
    }

    @Autowired
    public CarController(UserService userService, CarService carService) {
        this.userService = userService;
        this.carService = carService;
    }

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
            model.addAttribute("error", "Error: Ford " + "\"" + carModel + " " + vehicleGeneration + "\"" + " nothing was found," +
                " perhaps you were mistaken or we have not yet managed to add this car");
            return "engine";
        }
        String brand = StringFormatter.capitalizeFirstLetter(car.getBrand());

        carModel = StringFormatter.capitalizeFirstLetter(car.getModel());
        String newVehicleGeneration = "Car vehicle generation: " + toRoman(Integer.parseInt(car.getVehicleGeneration()));
        String price = "Price: " + car.getPrice() + "$";
        String power = "Car power: " + car.getPower() + " hp";
        String engineCapacity = "Engine capacity: " + car.getEngineCapacity() + "L";
        String maximumSpeed = "Max speed: " + car.getMaximumSpeed() + " km/h";
        String fullMass = "Full mass: " + car.getFullMass() + " kg";
        String carClass = "Class car: " + car.getCarClass();
        model.addAttribute("idCar", car.getId());
        model.addAttribute("model", brand + " " + carModel);
        model.addAttribute("newVehicleGeneration", newVehicleGeneration);
        model.addAttribute("price", price);
        model.addAttribute("power", power);
        model.addAttribute("engineCapacity", engineCapacity);
        model.addAttribute("maximumSpeed", maximumSpeed);
        model.addAttribute("fullMass", fullMass);
        model.addAttribute("carClass", carClass);
        model.addAttribute("img", "../image/car_picture/" + car.getPictureNumber() + ".png");
        return "engine";
    }

    @GetMapping(value = "/compare")
    public String compare(@RequestParam("position1") String carForComparison1,
                          @RequestParam("position2") String carForComparison2,
                          Model model,@AuthenticationPrincipal UserDetails principal) {
        String username = principal.getUsername();
        UserEntity user = userService.findByUsername(username);
        String idCar1=StringFormatter.extractIdFromDescription(carForComparison1);
        String idCar2=StringFormatter.extractIdFromDescription(carForComparison2);
        CarEntity car1 = carService.findCar(idCar1);
        CarEntity car2 = carService.findCar(idCar2);
        model.addAttribute("name", user.getName());
        model.addAttribute("age", user.getAge());
        model.addAttribute("resultCompareCar", CompareCar.buildCompareResult(car1, car2));
        return "personal-area";
    }


}
