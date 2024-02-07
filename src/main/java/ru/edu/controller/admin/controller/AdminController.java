package ru.edu.controller.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.edu.entity.CarEntity;
import ru.edu.entity.UserSite;
import ru.edu.service.CarService;
import ru.edu.service.ReportService;
import ru.edu.service.UserService;
import java.util.List;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private UserService userService;
    private CarService carService;
    private ReportService reportService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

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
        List<UserSite> listUser;
        listUser = userService.findAllUserByRoleUser();
        model.addAttribute("userList", listUser);
        return "admin-panel";
    }

    @GetMapping(value = "/user-report")
    public String userReport(Model model) {
        List<String>userReport=reportService.getAllReports();
        model.addAttribute("user_reports", userReport);
        return "user-report";
    }

    @GetMapping(value = "/blocked-user")
    public String blockedUser(Model model) {
        List<UserSite> users = userService.findAllUserByRoleBlocked();
        model.addAttribute("userList", users);
        return "blocked-user";
    }

    @GetMapping(value = "/all-car")
    public String getAllCar(Model model) {
        List<CarEntity> cars = carService.findAllCar();
        model.addAttribute("carList", cars);
        return "all-cars";
    }

    @GetMapping(value = "/update")
    public String updateCar() {
        return "update-car";
    }


}
