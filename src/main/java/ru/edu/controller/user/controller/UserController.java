package ru.edu.controller.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ru.edu.utils.StringFormatter;
import ru.edu.utils.ToRomanNumerals;


@Controller
@RequestMapping
public class UserController {

    private UserService userService;
    private CarService carService;

    public UserController() {
    }

    @Autowired
    public UserController(UserService userService, CarService carService) {
        this.userService = userService;
        this.carService = carService;
    }

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public String welcomePage() {
        return "engine";
    }

    @GetMapping("/register")
    public String signUpForm(Model model,
                             @RequestParam(required = false) String registrationStatus,
                             @RequestParam(required = false) String newUsername) {
        if ("failed".equals(registrationStatus)) {
            model.addAttribute("registrationFailed", newUsername);
        }
        return "register";
    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/create/account")
    public String createAccount(Model model,
                                @RequestParam(required = false) String userName) {
        if (userName != null) {
            model.addAttribute("username", userName);
        }
        return "personal-data";
    }


    @GetMapping(value = "/engine/me")
    public String personalArea(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserSite user = userService.findByUsername(username);
        model.addAttribute("userRole", user.getRole());
        model.addAttribute("name", user.getName());
        model.addAttribute("age", user.getAge());
        String favoriteCar1 = user.getFavoriteCar1();
        String favoriteCar2 = user.getFavoriteCar2();

        if (favoriteCar1 != null) {
            CarEntity car = carService.findCar(user.getFavoriteCar1());
            favoriteCar1 = String.format("%s %s %s",
                StringFormatter.capitalizeFirstLetter(car.getBrand()),
                StringFormatter.capitalizeFirstLetter(car.getModel()),
                ToRomanNumerals.toRoman(Integer.parseInt(car.getVehicleGeneration())));

        }
        if (favoriteCar2 != null) {
            CarEntity car = carService.findCar(user.getFavoriteCar2());
            favoriteCar2 = String.format("%s %s %s",
                StringFormatter.capitalizeFirstLetter(car.getBrand()),
                StringFormatter.capitalizeFirstLetter(car.getModel()),
                ToRomanNumerals.toRoman(Integer.parseInt(car.getVehicleGeneration())));

        }
        model.addAttribute("position1", favoriteCar1);
        model.addAttribute("position2", favoriteCar2);
        return "personal-area";
    }

    @GetMapping(value = "/report")
    public String report() {
        return "user-message";
    }

    @GetMapping("/help")
    public String help() {
        return "help";
    }

    @GetMapping("/helpEU")
    public String helpEU() {
        return "helpEU";
    }


}
