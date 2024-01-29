package ru.edu.controller.car.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edu.entity.CarEntity;
import ru.edu.entity.UserSite;
import ru.edu.service.CarService;
import ru.edu.service.UserService;




import static ru.edu.utils.CompareCar.compareCar;
import static ru.edu.utils.ToRomanNumerals.toRoman;

@Controller
@RequestMapping(value = "/car")
public class CarController {
    UserService userService;
    CarService carService;

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
        String brand = car.getBrand();
        brand = Character.toUpperCase(brand.charAt(0)) + brand.substring(1);
        String carModel1 = Character.toUpperCase(carModel.charAt(0)) + carModel.substring(1);
        String newVehicleGeneration = "Car vehicle generation: " + toRoman(Integer.parseInt(car.getVehicleGeneration()));
        String price = "Price: " + car.getPrice() + "$";
        String power = "Car power: " + car.getPower() + " hp";
        String engineCapacity = "Engine capacity: " + car.getEngineCapacity() + "L";
        String maximumSpeed = "Max speed: " + car.getMaximumSpeed() + " km/h";
        String fullMass = "Full mass: " + car.getFullMass() + " kg";
        String carClass = "Class car: " + car.getCarClass();
        model.addAttribute("idCar", car.getId());
        model.addAttribute("model", brand + " " + carModel1);
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
    public String compare(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserSite user = userService.findByUsername(username);
        CarEntity car1 = carService.findCar(user.getFavoriteCar1());
        CarEntity car2 = carService.findCar(user.getFavoriteCar2());
        model.addAttribute("name", user.getName());
        model.addAttribute("age", user.getAge());
        model.addAttribute("resultCompareCar",compareCar(car1,car2));
        return "personal-area";
    }

    @Autowired
    public void setCarService(CarService carService) {
        this.carService = carService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
