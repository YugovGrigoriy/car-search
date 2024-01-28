package ru.edu.controller.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping
public class UserController {


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public String welcomePage() {
        return "engine";
    }

    @GetMapping("/register")
    public String signUpForm(Model model,
                             @RequestParam(required = false) String registrationStatus,
                             @RequestParam(required = false) String newUsername) {
        if ("failed".equals(registrationStatus)) {
            model.addAttribute("registrationFailed", newUsername);
        }
        return "register";
    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/create/account")
    public String createAccount(Model model,
                                @RequestParam(required = false) String userName) {
        if (userName != null) {
            model.addAttribute("username", userName);
        }
        return "personal-data";
    }

    @GetMapping("/logout")
    public String logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.clearContext();
        }
        return "/login";
    }

    @GetMapping("/help")
    public String help() {
        return "help";
    }
    @GetMapping("/helpEU")
    public String helpEU() {
        return "helpEU";
    }

}
