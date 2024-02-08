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
import ru.edu.service.UserService;
import ru.edu.utils.StringFormatter;
import ru.edu.utils.ToRomanNumerals;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping
public class UserController {

    private UserService userService;


    public UserController() {
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
        if ("failedEasyPassword".equals(registrationStatus)) {
            model.addAttribute("registrationFailed", "the password is easy, use at least 1 uppercase, 1 lowercase and 1 number and a length of 8 characters");
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
        List<CarEntity> favoriteCars = user.getFavoriteCars();
        List<String> favoritePosition = new ArrayList<>();
        if (!favoriteCars.isEmpty()) {
            for (int i = 0; i < favoriteCars.size(); i++) {
                CarEntity car = favoriteCars.get(i);
                favoritePosition.add(String.format("%s: %s %s %s (id:%s)",
                    i + 1,
                    StringFormatter.capitalizeFirstLetter(car.getBrand()),
                    StringFormatter.capitalizeFirstLetter(car.getModel()),
                    ToRomanNumerals.toRoman(Integer.parseInt(car.getVehicleGeneration())),
                    car.getId()));
            }
            model.addAttribute("favoritePosition", favoritePosition);
        }

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
