package ru.edu.controller.admin.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping(value = "/admin/api")
public class ApiAdminController {
    UserService userService;

    @PostMapping("/add-user/blockList")
    public void blockUser(@RequestParam("ID") long id,
                          HttpServletResponse response) throws IOException {

            if(id>0) {
                userService.changeRole("BLOCKED", id);
            }

        response.sendRedirect("/admin/blocked-user");

    }

    @PostMapping("/add-user/unBlock")
    public void unBlockUser(@RequestParam("ID") long id,
                          HttpServletResponse response) throws IOException {
        if(id>0) {
            userService.changeRole("USER", id);
        }
        response.sendRedirect("/admin/blocked-user");
    }


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}

