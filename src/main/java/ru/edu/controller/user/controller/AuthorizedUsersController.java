package ru.edu.controller.user.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.edu.entity.UserSite;
import ru.edu.service.UserService;


@Controller
@RequestMapping(value = "/engine")
public class AuthorizedUsersController {

    private UserService userService;



    @GetMapping(value = "/me")
    public String personalArea(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserSite userSite = userService.findByUsername(username);
        model.addAttribute("name", userSite.getName());
        model.addAttribute("age", userSite.getAge());
        return "personal-area";
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}




