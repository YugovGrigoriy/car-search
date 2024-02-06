package ru.edu.controller.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.edu.entity.CarEntity;
import ru.edu.entity.ReportEntity;
import ru.edu.entity.UserSite;
import ru.edu.service.CarService;
import ru.edu.service.ReportService;
import ru.edu.service.UserService;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    UserService userService;
    CarService carService;
    ReportService reportService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping(value = "/admin-dashboard")
    public String adminPanel(Model model) {
        List<UserSite> listUser;
        listUser = userService.findAllUserByRoleUser();
        model.addAttribute("userList", listUser);
        return "admin-panel";
    }

    @GetMapping(value = "/user-report")
    public String userReport(Model model) {
        List<String> res = new ArrayList<>();
        List<ReportEntity> reports = reportService.findAll();
        for (int i = 0; i < reports.size(); i++) {
            ReportEntity report = reports.get(i);
            String r = String.format("Report #%d: id=%d, report date: %s, name = %s, email:%s, message:%s",
                i + 1,
                report.getId(),
                report.getLocalDateTime(),
                report.getName(),
                report.getEmail(),
                report.getMessage());
            res.add(r);

        }
        model.addAttribute("user_reports", res);
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

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCarService(CarService carService) {
        this.carService = carService;
    }

    @Autowired
    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }
}
