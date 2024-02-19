package ru.edu.controller.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.edu.entity.ReportEntity;
import ru.edu.entity.UserEntity;
import ru.edu.service.ReportService;
import ru.edu.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
/**
 * The ApiUserController class handles API requests related to user operations.
 * It manages tasks such as user registration, updating user information, reporting issues, and handling user authentication.
 *
 * @Controller annotation indicates that this class serves the role of a controller in Spring MVC.
 * @RequestMapping (value = "/api", consumes = MediaType.ALL_VALUE) specifies the base URL path for all methods in this controller.
 */
@Controller

@RequestMapping(value = "/api", consumes = MediaType.ALL_VALUE)
public class ApiUserController {


    private UserService userService;
    private ReportService reportService;

    public ApiUserController() {
    }
    /**
     * Constructor with dependencies injection for ApiUserController.
     *
     * @param userService    The UserService instance to handle user-related operations.
     * @param reportService  The ReportService instance to handle report-related operations.
     */
    @Autowired
    public ApiUserController(UserService userService, ReportService reportService) {
        this.userService = userService;
        this.reportService = reportService;
    }

    private static final Logger logger = LoggerFactory.getLogger(ApiUserController.class);

    /**
     * Handles the user registration process.
     *
     * @param user The UserEntity object containing user information.
     * @return A redirection URL based on the registration status.
     */
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
    /**
     * Updates the personal data of a user.
     *
     * @param userName The login of the user.
     * @param age The age of the user.
     * @param name The name of the user.
     * @param model The Model object for the view.
     * @return A redirection URL based on the update status.
     */
    @PostMapping("/user/me")
    public String fillPersonDate(@RequestParam("username") String userName,
                                 @RequestParam("age") int age,
                                 @RequestParam("name") String name,
                                 Model model) {
        if (userName == null || userName.isEmpty()) {
            logger.info("user not initialized, unknown error, time:+" + new Date());
            return "redirect:/login";
        }

        UserEntity currentUser = userService.findByUsername(userName);
        if (currentUser == null) {
            logger.error("User: " + userName + " not found");
            return "redirect:/login";
        }
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
    /**
     * Updates the age and name of the current user.
     *
     * @param age The new age of the user.
     * @param name The new name of the user.
     * @param model The Model object for the view.
     * @return A redirection URL based on the update status.
     */
    @PostMapping(value = "/update-user")
    public String updateUserAgeAndName(@RequestParam("age") int age,
                                       @RequestParam("name") String name,
                                       Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userService.findByUsername(username);
        if (user == null) {
            logger.error("User: " + username + " not found");
            return "redirect:/engine/me";
        }
        if (age > 100 || age < 1) {
            model.addAttribute("NotAvailableAge", "Age cannot be less than 0 or greater than 100");
            return "update-user";
        }
        user.setAge(age);
        user.setName(name);
        userService.updateUser(user);
        logger.info(String.format("%s update name:%s age:%s", username, name, age));
        return "redirect:/engine/me";
    }
    /**
     * Reports an issue with a user-specified name, message, and email.
     *
     * @param name The name of the report submitter.
     * @param message The message describing the issue.
     * @param email The email of the report submitter.
     * @return A redirection URL after submitting the report.
     */
    @PostMapping(value = "/report")
    public String report(@RequestParam("name") String name,
                         @RequestParam("message") String message,
                         @RequestParam("email") String email) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        if (name == null || name.isEmpty()) {
            name = "default name";
        }
        if (message == null || message.isEmpty()) {
            return "redirect:/";
        }
        if (email == null || email.isEmpty()) {
            email = "email was not provided";
        }
        ReportEntity report = new ReportEntity(formattedDateTime, name, message, email);
        reportService.save(report);
        return "redirect:/";
    }
    /**
     * Logs out the current user from the system.
     *
     * @param request The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @param authentication The Authentication object for the current user.
     * @return A redirection URL after logging out.
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }


}
