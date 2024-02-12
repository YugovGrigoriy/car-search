package ru.edu.controller.car.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String addFavoriteCar(@RequestParam(value = "idCar") String carId,
                                 @AuthenticationPrincipal UserDetails principal) {
        if (carId == null) {
            logger.info(String.format("Unknown error carId==null |%s|%s|",principal.getUsername(),principal.getAuthorities()));
        }

        String username = principal.getUsername();
        UserEntity user = userService.findByUsername(username);
        List<CarEntity> favoriteCars = user.getFavoriteCars();
        favoriteCars.add(carService.findCar(carId));
        userService.updateUser(user);
        return "redirect:/";
    }

    @PostMapping(value = "/clear")
    public String clear(@AuthenticationPrincipal UserDetails principal) {
        String username = principal.getUsername();
        UserEntity user = userService.findByUsername(username);
        user.setFavoriteCars(new ArrayList<>());
        userService.updateUser(user);
        return "redirect:/engine/me";
    }


}
