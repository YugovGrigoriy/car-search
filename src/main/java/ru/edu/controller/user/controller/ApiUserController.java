package ru.edu.controller.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.edu.entity.ReportEntity;
import ru.edu.entity.UserEntity;
import ru.edu.service.ReportService;
import ru.edu.service.UserService;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller

@RequestMapping(value = "/api", consumes = MediaType.ALL_VALUE)
public class ApiUserController {


    private UserService userService;
    private ReportService reportService;

    public ApiUserController() {
    }

    @Autowired
    public ApiUserController(UserService userService, ReportService reportService) {
        this.userService = userService;
        this.reportService = reportService;
    }

    private static final Logger logger = LoggerFactory.getLogger(ApiUserController.class);


    @PostMapping("/create")
    public String signUpForm(@ModelAttribute UserEntity user) {
        if (user.getUsername() == null) {
            logger.info("user not initialized, unknown error, time:+" + new Date());
            return "redirect:/registration";
        }

        if (userService.saveUser(user)) {
            logger.info(String.format("user:%s successfully registered", user.getUsername()));
            String username = user.getUsername();
            return "redirect:/create/account?userName=" + username;
        } else {
            String newUsername = userService.generateLogin(user.getUsername());
            return "redirect:/registration?registrationStatus=failed&newUsername=" + newUsername;
        }
    }

    @PostMapping("/user/me")
    public String fillPersonDate(@RequestParam("username") String userName,
                                 @RequestParam("age") int age,
                                 @RequestParam("name") String name,
                                 Model model) {
        if (userName == null||userName.isEmpty()) {
            logger.info("user not initialized, unknown error, time:+" + new Date());
            return "redirect:/login";
        }
        UserEntity currentUser = userService.findByUsername(userName);
        if (age > 100 || age < 1) {
            model.addAttribute("NotAvailableAge", "Age cannot be less than 0 or greater than 100");
            model.addAttribute("username", userName);
            return "personal-data";
        }
        currentUser.setName(name);
        currentUser.setAge(age);
        userService.updateUser(currentUser);
        logger.info(String.format("%s update name:%s age:%s", userName, name, age));
        return "redirect:/login";

    }

    @PostMapping(value = "/report")
    public String report(@RequestParam("name") String name,
                         @RequestParam("message") String message,
                         @RequestParam("email") String email) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        if (name == null||name.isEmpty()) {
            name = "default name";
        }
        if (message == null||message.isEmpty()) {
            return "redirect:/";
        }
        if (email == null||email.isEmpty()) {
            email = "email was not provided";
        }
        ReportEntity report = new ReportEntity(formattedDateTime, name, message, email);
        reportService.save(report);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }


}
