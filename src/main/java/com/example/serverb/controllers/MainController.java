package com.example.serverb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {
    @GetMapping("/acceptRequest/{currentId1}/{requestId}")
    public String acceptRequest(@PathVariable int currentId1, @PathVariable int requestId){

        //FriendList fl = new FriendList()
        //fs.saveInFriendList
        System.out.println(requestId+ "   "+currentId1);

        //requestService.deleteRequest(requestService.findRequestById(requestId).orElseThrow());
        return "redirect:/";
    }

}
