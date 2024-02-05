package ru.edu.controller.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.edu.entity.UserSite;
import ru.edu.service.UserService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    UserService userService;
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
        String fileContent = null;
        try (FileReader reader = new FileReader("logs/user_report.txt");
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("<br>");
            }
            fileContent = content.toString();

        } catch (IOException ex) {
            logger.info(ex.toString());
        }
        if (fileContent == null) {
            fileContent = "no complaints";
        }
        model.addAttribute("user_report", fileContent);
        return "user-report";
    }
    @GetMapping(value = "/blocked-user")
    public String blockedUser(Model model){
        List<UserSite>users=userService.findAllUserByRoleBlocked();
        model.addAttribute("userList",users);
        return "blocked-user";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
