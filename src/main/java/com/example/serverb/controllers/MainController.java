package com.example.serverb.controllers;

import com.example.serverb.entities.FriendList;
import com.example.serverb.entities.Request;
import com.example.serverb.services.FriendListService;
import com.example.serverb.services.RequestService;
import com.example.serverb.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private RestTemplate restTemplate = new RestTemplate();

    final String foreignIp = "http://localhost:8080";
    final String homeIp = "http://localhost:9091";


    List<Request> requestList = new ArrayList<>();
    int currentId = 0;


    UserService userService;
    FriendListService friendListService;
    RequestService requestService;

    public MainController(UserService userService, FriendListService friendListService, RequestService requestService) {
        this.userService = userService;
        this.friendListService = friendListService;
        this.requestService = requestService;
    }

    @GetMapping("/")
    public String getIndex(Model model) {
        requestList = requestService.findRequestsByUserId(currentId);

        model.addAttribute("myRequestList", requestList);
        model.addAttribute("currentId1", currentId);

        return "index";
    }

    //Accept Request
    @GetMapping("/acceptRequest/{currentId1}/{requestId}")
    public String acceptRequest(@PathVariable int currentId1, @PathVariable int requestId, Model model) {
        Request request = requestService.findRequestById(requestId).get();
        friendListService.saveInFriendList(new FriendList(request.getUser(), request.getUserEmail(), request.getForeignUserId(), request.getForeignUserEmail(), request.getSenderIp()));

        requestList = requestService.findRequestsByUserId(currentId1);
        currentId = currentId1;


        Map<String, String> reqMap = new HashMap<>();
        String method = "response";
        String path = foreignIp + "/response";
        String responseType = "confirmed";
        String responseFromRequest = "{" + method + ": "+ responseType+" "+request.getForeignUserId() +" "+request.getForeignUserEmail()+" "+ request.getUser().getId() + " " + request.getUserEmail() + " " + request.getReceiverIp() + " " + "v1" + "}";
        reqMap.put("confirmation", responseFromRequest);
        ResponseEntity response = restTemplate.postForEntity(path, reqMap, String.class);
        model.addAttribute("confirmation", response.getBody());
        System.out.println(response.getBody());


        requestService.deleteRequest(request);

        return "redirect:/";
    }

    //Decline Request
    @GetMapping("/declineRequest/{requestId}")
    public String declineRequest(@PathVariable int requestId) {
        Request request = requestService.findRequestById(requestId).get();
        requestService.deleteRequest(request);
        currentId = request.getUser().getId();
        requestList = requestService.findRequestsByUserId(currentId);

        return "redirect:/";
    }


    @GetMapping("/friendList/{currentId1}")
    public String getFriendList(@PathVariable int currentId1, Model model) {
        List<FriendList> friendList = friendListService.findFriendListByUserId(currentId1);
        model.addAttribute("friendList", friendList);
        return "friendList";

    }

}
