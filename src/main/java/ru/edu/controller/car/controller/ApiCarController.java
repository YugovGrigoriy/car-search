package ru.edu.controller.car.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.entity.UserSite;
import ru.edu.service.UserService;


import java.io.IOException;

@RestController
@RequestMapping(value = "/api/car", consumes = MediaType.ALL_VALUE)
public class ApiCarController {
    UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(ApiCarController.class);

    @PostMapping(value = "/add/favorite")
    public void addFavoriteCar(@RequestParam(value = "idCar") String carId,
                               HttpServletResponse response) throws IOException {
        if (carId == null) {
            logger.info(" ");//todo решить что делать
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserSite user = userService.findByUsername(username);
        if(user.getFavoriteCar1() != null) {
            if(user.getFavoriteCar2()==null){
                user.setFavoriteCar2(carId);
            } else if (user.getFavoriteCar2()!=null) {
                user.setFavoriteCar1(carId);
            }

        }if(user.getFavoriteCar1() == null){
            user.setFavoriteCar1(carId);
        }
        userService.updateUser(user);
        response.sendRedirect("/engine/me");
    }
    @PostMapping(value = "/clear")
    public void clear(HttpServletResponse response) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserSite user = userService.findByUsername(username);
        user.setFavoriteCar1(null);
        user.setFavoriteCar2(null);
        userService.updateUser(user);
        response.sendRedirect("/engine/me");
    }


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
