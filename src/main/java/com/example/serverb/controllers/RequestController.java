package com.example.serverb.controllers;

import com.example.serverb.entities.Request;
import com.example.serverb.entities.User;
import com.example.serverb.services.FriendListService;
import com.example.serverb.services.RequestService;
import com.example.serverb.services.UserService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Controller
public class RequestController {

    final String homeIp = "http://localhost:9091";
    final String foreignIp = "http://localhost:8080";

    RequestService requestService;
    UserService userService;
    FriendListService fs;
    Map<String,String> newRequests = new HashMap<>();

    private RestTemplate restTemplate = new RestTemplate();

    public RequestController(RequestService requestService, UserService userService, FriendListService fs) {
        this.requestService = requestService;
        this.userService = userService;
        this.fs = fs;
    }

    @GetMapping("/all")
    public Iterable<Request> fetchAllRequests() {
        return requestService.findAllRequests();
    }

    @GetMapping("/{userId}")
    public List<Request> findRequestsByUserId(@PathVariable int userId) {
        return requestService.findRequestsByUserId(userId);
    }

    @PostMapping("/save")
    public Request addRequest(@RequestBody Request request) {
        return requestService.saveRequest(request);
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Boolean> deleteRequest(@PathVariable int id) throws ResourceNotFoundException {
        Request request = requestService.findRequestById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found for this id :: " + id));

        requestService.deleteRequest(request);
        Map<String, Boolean> response = new HashMap<>();
        response.put("request deleted", Boolean.TRUE);
        return response;
    }

    @PostMapping("/sendFriendRequest")
    public String sendGreeting(@RequestParam String f_email,@RequestParam String currentemail,@RequestParam String userid, Model model) {
        System.out.println(f_email);
        String foreignEmail = f_email;
        String currentUserId = userid;
        String receiverIP = foreignIp + "/friendship";
        String userEmail = currentemail;
        Map<String, String> reqMap = new HashMap<>();
        String method = "request";
        String requestForFriendship = "{" + method + ": " + userEmail + " " + currentUserId + " " + homeIp + " " + foreignEmail + " " + foreignIp + " " + "v1" + "}";
        reqMap.put("request", requestForFriendship);
        ResponseEntity response = restTemplate.postForEntity(receiverIP, reqMap, String.class);
        model.addAttribute("request", response.getBody());
        model.addAttribute("userEmail", userEmail);
        System.out.println(response.getBody());

        return "Your request has been received.";
    }

    @PostMapping("/friendship")
    public ResponseEntity<String> postGreeting(@RequestBody Map<String,String> req) {
        String friendshipRequest = req.get("request");
        String[] requestDetails = friendshipRequest.split("\\s+");
        System.out.println(requestDetails[3]);
        User user = userService.findUserByEmail(requestDetails[5]);
        String foreignEmail = requestDetails[2];
        int foreignId = Integer.parseInt(requestDetails[3].substring(3));
        String senderIp = requestDetails[4] ;
        String receiverIp = requestDetails[6];

        if(user!= null){
            // create request in DB
            Request rq = new Request(user, user.getEmail(), foreignId, foreignEmail, senderIp, receiverIp);

            if(requestService.findRequestByUserAndForeignEmail(user,foreignEmail)==null){
                requestService.saveRequest(rq);
                return ResponseEntity.ok("TRUE Request created in server A."+req.get("request")+" On "+new Date());
            }
        }
        return ResponseEntity.ok("User does not exist in Server A." +req.get("request"));
    }
}
