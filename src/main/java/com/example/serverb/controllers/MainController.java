package com.example.serverb.controllers;

import com.example.serverb.entities.FriendList;
import com.example.serverb.repos.FriendListRepository;
import com.example.serverb.repos.UserRepository;
import com.example.serverb.services.FriendListService;
import com.example.serverb.services.RequestService;
import com.example.serverb.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {
    UserService userService;
    FriendListService friendListService;
    RequestService requestService;

    public MainController(UserService userService, FriendListService friendListService, RequestService requestService) {
        this.userService = userService;
        this.friendListService = friendListService;
        this.requestService = requestService;
    }

    @GetMapping("/acceptRequest/{currentId1}/{requestId}")
    public String acceptRequest(@PathVariable int currentId1, @PathVariable int requestId){
//      friendListService.saveInFriendList(userService.findUserById(currentId1),requestId,)
        System.out.println(requestId+ "   "+currentId1);

        //requestService.deleteRequest(requestService.findRequestById(requestId).orElseThrow());
        return "redirect:/";
    }

    @DeleteMapping("/declineRequest/{currentId1}/{requestId}")
    public String declineRequest(@PathVariable int currentId1, @PathVariable int requestId){
//      friendListService.saveInFriendList(userService.findUserById(currentId1),requestId,)
        System.out.println(requestId + "   " + currentId1);

        requestService.deleteRequestByRequestId(requestService.findRequestById(requestId).orElseThrow());
        return "redirect:/";
    }

}
