package com.example.serverb.controllers;

import com.example.serverb.entities.Request;
import com.example.serverb.entities.User;
import com.example.serverb.services.RequestService;
import com.example.serverb.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;


@Controller
public class LoginController {

    int currentUserId = 0;
    String currentUserEmail = "";

    UserService userService;
    RequestService requestService;
    List<Request> myRequests = new ArrayList<>();

    public LoginController(UserService userService, RequestService requestService) {
        this.userService = userService;
        this.requestService = requestService;
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/")
    public ModelAndView login(@RequestParam String email, Model model) {
        System.out.println(email);
        currentUserEmail = email;

        ModelAndView mv = new ModelAndView("index");
        ModelAndView loginMv = new ModelAndView("login");
        User currentUser = userService.findUserByEmail(email);
        if (currentUser != null) {
            currentUserId = currentUser.getId();
            myRequests = requestService.findRequestsByUserId(currentUserId);

            model.addAttribute("myRequestList",myRequests);
            model.addAttribute("currentEmail","Email: "+email);
            model.addAttribute("currentId","Id:"+currentUserId);
            model.addAttribute("currentId1",currentUserId);
            return mv;
        }
        return loginMv;
    }
}
