package ru.edu.controller.car.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edu.entity.CarEntity;
import ru.edu.entity.UserEntity;
import ru.edu.service.CarService;
import ru.edu.service.UserService;

import java.util.ArrayList;
import java.util.List;
/**
 * The ApiCarController class handles API requests related to car operations.
 * It is responsible for managing favorite cars for users and performing related tasks through API endpoints.
 *
 * @Controller annotation indicates that this class serves the role of a controller in Spring MVC.
 * @RequestMapping (value = "/api/car", consumes = MediaType.ALL_VALUE) specifies the base URL path for all methods in this controller.
 */
@Controller
@RequestMapping(value = "/api/car", consumes = MediaType.ALL_VALUE)
public class ApiCarController {
    private UserService userService;
    private CarService carService;
    private static final Logger logger = LoggerFactory.getLogger(ApiCarController.class);

    public ApiCarController() {
    }
    /**
     * Constructor with dependencies injection for ApiCarController.
     *
     * @param userService The UserService instance to handle user-related operations.
     * @param carService  The CarService instance to handle car-related operations.
     */
    @Autowired
    public ApiCarController(UserService userService, CarService carService) {
        this.userService = userService;
        this.carService = carService;
    }
    /**
     * API endpoint to add a car to the user's favorite list.
     *
     * @param carId The ID of the car to add to favorites.
     * @return The view name for redirecting to the home page.
     */
    @PostMapping(value = "/add/favorite")
    public String addFavoriteCar(@RequestParam(value = "idCar") String carId) {
        if (carId.isEmpty()) {
            logger.info("Unknown error carId==null");
            return "redirect:/";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userService.findByUsername(username);
        if (user == null) {
            logger.error("User: " + username + " not found");
            return "redirect:/";
        }
        List<CarEntity> favoriteCars = user.getFavoriteCars();
        CarEntity car = carService.findCar(carId);
        if (car == null) {
            logger.error("Car id: " + carId + " not found");
            return "redirect:/";
        }
        favoriteCars.add(car);
        userService.updateUser(user);
        return "redirect:/";

    }
    /**
     * API endpoint to clear the user's favorite car list.
     *
     * @return The view name for redirecting to the user's page.
     */
    @PostMapping(value = "/clear")
    public String clear() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userService.findByUsername(username);
        if(user==null){
            logger.error("User: " + username + " not found");
            return "redirect:/";
        }
            user.setFavoriteCars(new ArrayList<>());
            userService.updateUser(user);
            return "redirect:/engine/me";


    }


}
