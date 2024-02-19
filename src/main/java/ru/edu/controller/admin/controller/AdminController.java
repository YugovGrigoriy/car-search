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
/**
 * The AdminController class handles HTTP requests related to administrative functions.
 * It is responsible for managing users, cars, reports, and administrative tasks.
 *
 * @Controller annotation indicates that this class serves the role of a controller in Spring MVC.
 * @RequestMapping (value = "/admin") specifies the base URL path for all methods in this controller.
 */

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private UserService userService;
    private CarService carService;
    private ReportService reportService;


    public AdminController() {
    }
    /**
     * Constructor with dependencies injection for AdminController.
     *
     * @param userService   The UserService instance to handle user-related operations.
     * @param carService    The CarService instance to handle car-related operations.
     * @param reportService The ReportService instance to handle report-related operations.
     */

    @Autowired
    public AdminController(UserService userService, CarService carService, ReportService reportService) {
        this.userService = userService;
        this.carService = carService;
        this.reportService = reportService;
    }
    /**
     * Displays the admin panel with a list of all users with the role 'User'.
     *
     * @param model The model to add attributes for rendering the view.
     * @return The view name for the admin panel.
     */
    @GetMapping(value = "/admin-dashboard")
    public String adminPanel(Model model) {
        List<UserEntity> allUsers=userService.findAllUserByRoleUser();
        model.addAttribute("userList", AdminUtils.getListUser(allUsers));
        return "admin-panel";
    }
    /**
     * Displays the user report page with a list of all reports.
     *
     * @param model The model to add attributes for rendering the view.
     * @return The view name for the user report page.
     */
    @GetMapping(value = "/user-report")
    public String userReport(Model model) {
        List<ReportEntity>userReport=reportService.findAll();
        model.addAttribute("user_reports", AdminUtils.getListReport(userReport));
        return "user-report";
    }
    /**
     * Displays the page with a list of all blocked users.
     *
     * @param model The model to add attributes for rendering the view.
     * @return The view name for the blocked user page.
     */
    @GetMapping(value = "/blocked-user")
    public String blockedUser(Model model) {
        List<UserEntity> users = userService.findAllUserByRoleBlocked();
        model.addAttribute("userList", AdminUtils.getListUser(users));
        return "blocked-user";
    }
    /**
     * Displays the page with a list of all cars.
     *
     * @param model The model to add attributes for rendering the view.
     * @return The view name for the page displaying all cars.
     */
    @GetMapping(value = "/all-car")
    public String getAllCar(Model model) {
        List<CarEntity> cars = carService.findAllCar();
        model.addAttribute("carList", AdminUtils.getListCar(cars) );
        return "all-cars";
    }
    /**
     * Displays the page for updating a car with an optional parameter for car not found.
     *
     * @param carNotFound Optional parameter indicating if the car was not found.
     * @param model       The model to add attributes for rendering the view.
     * @return The view name for the update car page.
     */
    @GetMapping(value = "/update")
    public String updateCar(@RequestParam(required = false) String carNotFound,Model model) {
        model.addAttribute("carNotFound",carNotFound);
        return "update-car";
    }


}
