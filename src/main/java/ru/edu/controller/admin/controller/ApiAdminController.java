package ru.edu.controller.admin.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edu.service.CarService;
import ru.edu.service.UserService;


@Controller
@RequestMapping(value = "/admin/api")
public class ApiAdminController {
    UserService userService;
    CarService carService;

    @PostMapping("/add-user/blockList")
    public String blockUser(@RequestParam("ID") long id, Model model) {

        try {
            if (id > 0) {
                userService.changeRole("BLOCKED", id);
            }
        } catch (NullPointerException ex) {
            model.addAttribute("err", "user not found");
            return "blocked-user";
        }
        return "redirect:/admin/blocked-user";

    }

    @PostMapping("/add-user/unBlock")
    public String unBlockUser(@RequestParam("ID") long id, Model model) {
        try {
            if (id > 0) {
                userService.changeRole("USER", id);
            }
        } catch (NullPointerException ex) {
            model.addAttribute("err", "user not found");
            return "blocked-user";
        }
        return "redirect:/admin/blocked-user";
    }

    @PostMapping(value = "/update-car-price")
    public String updateCarPrice(@RequestParam("newPrice") int newPrice,
                                 @RequestParam("carId") long carId) {
        carService.updatePriceCar(carId, newPrice);
        return "redirect:/admin/admin-dashboard";
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

