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
import org.springframework.web.bind.annotation.*;
import ru.edu.entity.ReportEntity;
import ru.edu.entity.UserSite;
import ru.edu.service.ReportService;
import ru.edu.service.UserService;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    //logger.info("trying to register with an existing login: "+username);

    @PostMapping("/create")
    public String signUpForm(@ModelAttribute UserSite user) {

        if (userService.saveUser(user)) {
            logger.info("user:" + user.getUsername() + " successfully registered");
            String userName = user.getUsername();
            return "redirect:/create/account?userName=" + userName;
        } else {
            String newUsername = userService.generateLogin(user.getUsername());
            return "redirect:/register?registrationStatus=failed&newUsername=" + newUsername;
        }
    }

    @PostMapping("/user/me")
    public String fillPersonDate(@RequestParam("username") String userName,
                                 @RequestParam("age") int age,
                                 @RequestParam("name") String name){

        UserSite currentUser = userService.findByUsername(userName);
        currentUser.setName(name);
        currentUser.setAge(age);
        userService.updateUser(currentUser);
        logger.info("this user update name:" + name + " age:" + age);
        return "redirect:/login";

    }

    @PostMapping(value = "/report")
    public String report(@RequestParam("name") String name,
                         @RequestParam("message") String message,
                         @RequestParam("email") String email) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        if(name==null){
            name="default name";
        }
        if(message==null){
           return "redirect:/";
        }
        if(email==null){
            email="email was not provided";
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
