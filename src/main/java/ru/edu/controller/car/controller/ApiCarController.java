package ru.edu.controller.car.controller;

import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RestController;
import ru.edu.entity.UserSite;
import ru.edu.service.UserService;


import java.io.IOException;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "/api/car", consumes = MediaType.ALL_VALUE)
public class ApiCarController {
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(ApiCarController.class);

    public ApiCarController() {
    }
@Autowired
    public ApiCarController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/add/favorite")
    public String addFavoriteCar(@RequestParam(value = "idCar") String carId){
        if (carId == null) {
            logger.info(" ");//todo решить что делать
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserSite user = userService.findByUsername(username);
        if(user.getFavoriteCar1() != null) {
            if(user.getFavoriteCar2()==null){
                user.setFavoriteCar2(carId);
user.setFavoriteCars(new ArrayList<>());
            } else if (user.getFavoriteCar2()!=null) {
                user.setFavoriteCar1(carId);
            }

        }if(user.getFavoriteCar1() == null){
            user.setFavoriteCar1(carId);
        }
        userService.updateUser(user);
        return "redirect:/engine/me";
    }
    @PostMapping(value = "/clear")
    public String clear(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserSite user = userService.findByUsername(username);
        user.setFavoriteCar1(null);
        user.setFavoriteCar2(null);
        userService.updateUser(user);
        return "redirect:/engine/me";
    }



}
