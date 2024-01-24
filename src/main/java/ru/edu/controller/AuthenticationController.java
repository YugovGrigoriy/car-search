package ru.edu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.edu.entity.UserSite;
import ru.edu.exception.NameNotAvailableException;
import ru.edu.service.UserService;

@Controller
@RequestMapping
public class AuthenticationController {
    ModelAndView modelAndView = new ModelAndView();
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @GetMapping(value = "/car-search-engine")
    public String welcomePage() {
        logger.info("receiving a request for /car-search-engine");
        return "welcome-page";

    }

    // /register
    @GetMapping("/register")
    public ModelAndView signUpForm() {
        logger.info("get req register");
        modelAndView.setViewName("register");
        modelAndView.addObject("registrationFailed", "Sign Up");
        return modelAndView;
    }


    // todo:
    // api/users/create
    // api/users/update

    // api/cars/add
    // api/cars/update
    // api/cars/delete
    @PostMapping("/registration")
    public ModelAndView signUpForm(@ModelAttribute UserSite user) {

        try {
            userService.saveUser(user);
            logger.info("user:" + user.getUsername() + " successfully registered");
            modelAndView.addObject("username",user.getUsername());
            modelAndView.setViewName("personal-data");
            return modelAndView;
        } catch (NameNotAvailableException ex) {
            String newLogin = userService.generateLogin(user.getUsername());
            modelAndView.setViewName("register");
            modelAndView.addObject("registrationFailed", newLogin);
            return modelAndView;
        }
    }


    // user/me
    @PostMapping("/personal-area")
    public ModelAndView fillPersonDate(@RequestParam("username") String userName,
                               @RequestParam("age") int age,
                               @RequestParam("name") String name) {

        UserSite currentUser = userService.findByUsername(userName);
        currentUser.setName(name);
        currentUser.setAge(age);
        userService.updateUser(currentUser);
        logger.info("this user update name:"+name+" age:"+age);
        modelAndView.setViewName("personal-area");
        modelAndView.addObject("name", currentUser.getName());
        modelAndView.addObject("age", currentUser.getAge());
        return modelAndView;

    }

    @GetMapping(value = "/login")
    public String login() {

        return "login";
    }



    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


}
