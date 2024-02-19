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
import ru.edu.entity.UserEntity;
import ru.edu.service.UserService;
import ru.edu.utils.StringFormatter;
import ru.edu.utils.ToRomanNumerals;

import java.util.ArrayList;
import java.util.List;

/**
 * The UserController class manages user-related web requests and operations.
 * It includes methods for user registration, login, updating user information, accessing personal area, reporting issues, and providing help.
 */
@Controller
@RequestMapping
public class UserController {

    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ApiUserController.class);

    public UserController() {
    }
    /**
     * Constructor with dependencies injection for UserController.
     *
     * @param userService The UserService instance to handle user-related operations.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Displays the welcome page.
     *
     * @return The view name for the main page.
     */
    @GetMapping
    public String welcomePage() {
        return "engine";
    }
    /**
     * Displays the user registration form.
     *
     * @param model The Model object for the view.
     * @param registrationStatus The status of the registration process.
     * @param newUsername The new username if registration failed.
     * @return The view name for the user registration form.
     */
    @GetMapping("/registration")
    public String signUpForm(Model model,
                             @RequestParam(required = false) String registrationStatus,
                             @RequestParam(required = false) String newUsername) {
        if ("failed".equals(registrationStatus)) {
            model.addAttribute("registrationFailed", newUsername);
        }
        return "sign-up";
    }
    /**
     * Displays the user information update form.
     *
     * @return The view name for updating user information.
     */
    @GetMapping(value = "/change-user-information")
    public String updateUser() {
        return "update-user";
    }
    /**
     * Displays the login form.
     *
     * @return The view name for the login form.
     */
    @GetMapping("/login")
    public String login() {
        return "sign-in";
    }
    /**
     * Creates a new user account.
     *
     * @param model The Model object for the view.
     * @param userName The username of the user.
     * @return The view name for creating a new user account.
     */

    @GetMapping("/create/account")
    public String createAccount(Model model,
                                @RequestParam String userName) {
        model.addAttribute("username", userName);
        return "personal-data";
    }
    /**
     * Displays the personal area of the logged-in user.
     *
     * @param model The Model object for the view.
     * @return The view name for the personal area.
     */

    @GetMapping(value = "/engine/me")
    public String personalArea(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userService.findByUsername(username);
        if (user == null) {
            logger.error("User: " + username+" not found");
            return "redirect:/";
        }
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
    /**
     * Displays the report form for reporting issues.
     *
     * @return The view name for the report form.
     */
    @GetMapping(value = "/report")
    public String report() {
        return "user-message";
    }
    /**
     * Displays the help page in Russian.
     *
     * @return The view name for the help page in Russian.
     */
    @GetMapping("/help")
    public String help() {
        return "helpRU";
    }
    /**
     * Displays the help page in English.
     *
     * @return The view name for the help page in English.
     */
    @GetMapping("/helpEU")
    public String helpEU() {
        return "helpENG";
    }


}
