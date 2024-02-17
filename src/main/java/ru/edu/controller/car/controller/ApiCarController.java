package ru.edu.controller.car.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

@Controller
@RequestMapping(value = "/api/car", consumes = MediaType.ALL_VALUE)
public class ApiCarController {
    private UserService userService;
    private CarService carService;
    private static final Logger logger = LoggerFactory.getLogger(ApiCarController.class);

    public ApiCarController() {
    }

    @Autowired
    public ApiCarController(UserService userService, CarService carService) {
        this.userService = userService;
        this.carService = carService;
    }

    @PostMapping(value = "/add/favorite")
    public String addFavoriteCar(@RequestParam(value = "idCar") String carId) {
        if (carId.isEmpty()) {
            logger.info("Unknown error carId==null");
            return "redirect:/";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userService.findByUsername(username);
        List<CarEntity> favoriteCars = user.getFavoriteCars();
        favoriteCars.add(carService.findCar(carId));
        userService.updateUser(user);
        return "redirect:/";
    }

    @PostMapping(value = "/clear")
    public String clear() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userService.findByUsername(username);
        user.setFavoriteCars(new ArrayList<>());
        userService.updateUser(user);
        return "redirect:/engine/me";
    }


}
