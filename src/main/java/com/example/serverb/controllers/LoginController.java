package com.example.serverb.controllers;

import com.example.serverb.entities.User;
import com.example.serverb.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class LoginController {

    int currentUserId = 0;
    String currentUserEmail = "";
    UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }


    @PostMapping("/")
    public ModelAndView login(@RequestParam String email, Model model) {
        System.out.println(email);
        ModelAndView mv = new ModelAndView("index");
        ModelAndView loginMv = new ModelAndView("login");
        User currentUser = userService.findUserByEmail(email);
        if (currentUser != null) {
            currentUserId = currentUser.getId();
            System.out.println(currentUserId);
            model.addAttribute("currentEmail","Email: "+email);
            model.addAttribute("currentId","Id:"+currentUserId);
            return mv;

        }
        return loginMv;
    }
}
