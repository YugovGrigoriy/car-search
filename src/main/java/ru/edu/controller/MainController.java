package ru.edu.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.edu.entity.UserSite;


@Controller
@RequestMapping(value = "/main")
public class MainController {
//todo название класса
    ModelAndView modelAndView = new ModelAndView();

    @GetMapping("/person")
    public ModelAndView personArea(UserSite user) {
        String currentName = user.getName();
        int currentAge = user.getAge();
        modelAndView.setViewName("personal-area");
        modelAndView.addObject("name", currentName);
        modelAndView.addObject("age", currentAge);
        return modelAndView;
    }
    @GetMapping("/engine")
    public String mainPage(){
        return "engine";
    }

}


