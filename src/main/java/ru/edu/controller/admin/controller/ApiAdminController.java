package ru.edu.controller.admin.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edu.controller.car.controller.ApiCarController;
import ru.edu.entity.CarEntity;
import ru.edu.service.CarService;
import ru.edu.service.UserService;


@Controller
@RequestMapping(value = "/admin/api")
public class ApiAdminController {
    private UserService userService;
    private CarService carService;
    private static final Logger logger = LoggerFactory.getLogger(ApiAdminController.class);
    public ApiAdminController() {
    }
@Autowired
    public ApiAdminController(UserService userService, CarService carService) {
        this.userService = userService;
        this.carService = carService;
    }

    @PostMapping("/add-user/blockList")
    public String blockUser(@RequestParam("ID") long id, Model model,
                            @AuthenticationPrincipal UserDetails principal) {

        try {
            if (id > 0) {
                userService.changeRole("BLOCKED", id);
                logger.info(String.format("admin:%s blocked user with id %s",
                principal.getUsername(),
                id));
            }
        } catch (RuntimeException ex) {
        model.addAttribute("err", "user not found");
            return "blocked-user";
        }
        return "redirect:/admin/blocked-user";

    }

    @PostMapping("/add-user/unBlock")
    public String unBlockUser(@RequestParam("ID") long id, Model model,@AuthenticationPrincipal UserDetails principal) {
        try {
            if (id > 0) {
                userService.changeRole("USER", id);
                logger.info(String.format("admin:%s unblocked user with id %s",
                    principal.getUsername(),
                    id));
            }
        } catch (RuntimeException ex) {
            model.addAttribute("err", "user not found");
            return "blocked-user";
        }
        return "redirect:/admin/blocked-user";
    }

    @PostMapping(value = "/update-car-price")
    public String updateCarPrice(@RequestParam("newPrice") int newPrice,
                                 @RequestParam("carId") long carId,@AuthenticationPrincipal UserDetails principal) {
        CarEntity car=carService.findCar(String.valueOf(carId));
        if(car!=null){
            carService.updatePriceCar(carId, newPrice);
            logger.info(String.format("admin:%s changed the price of the car from ID: %s",
                principal.getUsername(),
                carId));
            return "redirect:/admin/admin-dashboard";
        }
        String carNotFound=String.format("car with id:%s not found",carId);
        return "redirect:/admin/update?carNotFound="+carNotFound;
    }


}

