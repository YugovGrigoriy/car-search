package ru.edu.controller.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edu.entity.CarEntity;
import ru.edu.entity.ReportEntity;
import ru.edu.entity.UserEntity;
import ru.edu.service.CarService;
import ru.edu.service.ReportService;
import ru.edu.service.UserService;
import ru.edu.utils.AdminUtils;


import java.util.List;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private UserService userService;
    private CarService carService;
    private ReportService reportService;


    public AdminController() {
    }

    @Autowired
    public AdminController(UserService userService, CarService carService, ReportService reportService) {
        this.userService = userService;
        this.carService = carService;
        this.reportService = reportService;
    }

    @GetMapping(value = "/admin-dashboard")
    public String adminPanel(Model model) {
        List<UserEntity> allUsers=userService.findAllUserByRoleUser();
        model.addAttribute("userList", AdminUtils.getListUser(allUsers));
        return "admin-panel";
    }

    @GetMapping(value = "/user-report")
    public String userReport(Model model) {
        List<ReportEntity>userReport=reportService.findAll();
        model.addAttribute("user_reports", AdminUtils.getListReport(userReport));
        return "user-report";
    }

    @GetMapping(value = "/blocked-user")
    public String blockedUser(Model model) {
        List<UserEntity> users = userService.findAllUserByRoleBlocked();
        model.addAttribute("userList", AdminUtils.getListUser(users));
        return "blocked-user";
    }

    @GetMapping(value = "/all-car")
    public String getAllCar(Model model) {
        List<CarEntity> cars = carService.findAllCar();
        model.addAttribute("carList", AdminUtils.getListCar(cars) );
        return "all-cars";
    }

    @GetMapping(value = "/update")
    public String updateCar(@RequestParam(required = false) String carNotFound,Model model) {
        model.addAttribute("carNotFound",carNotFound);
        return "update-car";
    }


}
