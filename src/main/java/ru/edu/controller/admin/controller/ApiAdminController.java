package ru.edu.controller.admin.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edu.entity.CarEntity;
import ru.edu.service.CarService;
import ru.edu.service.UserService;

/**
 * The ApiAdminController class handles API requests related to administrative functions.
 * It is responsible for managing users, cars, and performing administrative tasks through API endpoints.
 *
 * @Controller annotation indicates that this class serves the role of a controller in Spring MVC.
 * @RequestMapping (value = "/admin/api") specifies the base URL path for all methods in this controller.
 */
@Controller
@RequestMapping(value = "/admin/api")
public class ApiAdminController {
    private UserService userService;
    private CarService carService;
    private static final Logger logger = LoggerFactory.getLogger(ApiAdminController.class);

    public ApiAdminController() {
    }
    /**
     * Constructor with dependencies injection for ApiAdminController.
     *
     * @param userService The UserService instance to handle user-related operations.
     * @param carService  The CarService instance to handle car-related operations.
     */
    @Autowired
    public ApiAdminController(UserService userService, CarService carService) {
        this.userService = userService;
        this.carService = carService;
    }
    /**
     * API endpoint to unblock a user by ID.
     *
     * @param id    The ID of the user to block.
     * @param model The model to add attributes for rendering the view.
     * @return The view name for redirecting to the admin dashboard.
     */
    @PostMapping("/add-user/blockList")
    public String blockUser(@RequestParam("ID") long id, Model model) {

        try {
            if (id > 0&&id<Long.MAX_VALUE) {
                userService.changeRole("BLOCKED", id);
                logger.info(String.format("admin:%s blocked user with id %s",
                    SecurityContextHolder.getContext().getAuthentication().getName(),
                    id));
            }
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("err", "user not found");
            return "blocked-user";
        }
        return "redirect:/admin/blocked-user";

    }
    /**
     * API endpoint to unblock a user by ID.
     *
     * @param id    The ID of the user to unblock.
     * @param model The model to add attributes for rendering the view.
     * @return The view name for redirecting to the admin dashboard.
     */
    @PostMapping("/add-user/unBlock")
    public String unBlockUser(@RequestParam("ID") long id, Model model) {
        try {
            if (id > 0&&id<Long.MAX_VALUE) {
                userService.changeRole("USER", id);
                logger.info(String.format("admin:%s unblocked user with id %s",
                    SecurityContextHolder.getContext().getAuthentication().getName(),
                    id));
            }
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("err", "user not found");
            return "blocked-user";
        }
        return "redirect:/admin/blocked-user";
    }
    /**
     * API endpoint to update the price of a car by ID.
     *
     * @param newPrice The new price for the car.
     * @param carId    The ID of the car to update.
     * @return The view name for redirecting to the admin dashboard or update car page.
     */
    @PostMapping(value = "/update-car-price")
    public String updateCarPrice(@RequestParam("newPrice") int newPrice,
                                 @RequestParam("carId") long carId) {
        if (newPrice == 0) {
            return "redirect:/admin/update?carNotFound=price can not be 0";
        }
        if (carId == 0 || carId < 0) {
            return "redirect:/admin/update?carNotFound=car ID can not be 0";
        }

        CarEntity car = carService.findCar(String.valueOf(carId));
        if (car != null){
            carService.updatePriceCar(carId, newPrice);
            logger.info(String.format("admin:%s changed the price of the car from ID: %s",
                SecurityContextHolder.getContext().getAuthentication().getName(),
                carId));
            return "redirect:/admin/admin-dashboard";
        }
            String carNotFound = String.format("car with id:%s not found", carId);
            return "redirect:/admin/update?carNotFound=" + carNotFound;

    }


}

