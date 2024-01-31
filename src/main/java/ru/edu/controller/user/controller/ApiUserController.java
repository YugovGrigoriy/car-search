package ru.edu.controller.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import ru.edu.entity.UserSite;
import ru.edu.service.UserService;

import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(value = "/api", consumes = MediaType.ALL_VALUE)
public class ApiUserController {


    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(ApiUserController.class);
    //logger.info("trying to register with an existing login: "+username);

    @PostMapping("/create")
    public void signUpForm(@ModelAttribute UserSite user, HttpServletResponse response) throws IOException {

        if (userService.saveUser(user)) {
            logger.info("user:" + user.getUsername() + " successfully registered");
            String userName = user.getUsername();
            response.sendRedirect("/create/account?userName=" + userName);
        } else {
            String newUsername = userService.generateLogin(user.getUsername());
            response.sendRedirect("/register?registrationStatus=failed&newUsername=" + newUsername);
        }
    }

    @PostMapping("/user/me")
    public void fillPersonDate(HttpServletResponse response,
                               @RequestParam("username") String userName,
                               @RequestParam("age") int age,
                               @RequestParam("name") String name) throws IOException {

        UserSite currentUser = userService.findByUsername(userName);
        currentUser.setName(name);
        currentUser.setAge(age);
        userService.updateUser(currentUser);
        logger.info("this user update name:" + name + " age:" + age);
        response.sendRedirect("/login");

    }

    @PostMapping(value = "/report")
    public void report(@RequestParam("name") String name,
                       @RequestParam("message") String message,
                       @RequestParam("email") String email,
                       HttpServletResponse response) throws IOException {
        try (FileWriter writer = new FileWriter("logs/user_report.txt", true)) {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            writer.write("""

                message
                """);
            writer.write("--------------------------------" + "\n");
            writer.write(formattedDateTime + "\n");
            writer.write("name: " + name + "\n");
            writer.write("email: " + email + "\n");
            writer.write(message + "\n");
            writer.write("--------------------------------" + "\n");

        } catch (IOException ex) {
            logger.info(ex.toString());
        }
        response.sendRedirect("/");
    }
    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        response.sendRedirect("/");
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
