package com.example.serverb.controllers;

import com.example.serverb.entities.FriendList;
import com.example.serverb.entities.Request;
import com.example.serverb.entities.User;
import com.example.serverb.services.FriendListService;
import com.example.serverb.services.RequestService;
import com.example.serverb.services.UserService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Controller
public class RequestController {

    RequestService requestService;
    UserService userService;
    FriendListService fs;
    Map<String,String> newRequests = new HashMap<>();

    public RequestController(RequestService requestService,UserService userService, FriendListService fs){
        this.requestService = requestService;
        this.userService = userService;
        this.fs = fs;
    }

    @GetMapping("/all")
    public Iterable<Request> fetchAllRequests(){
        return requestService.findAllRequests();
    }

    @GetMapping("/{userId}")
    public List<Request> findRequestsByUserId(@PathVariable int userId){
        return requestService.findRequestsByUserId(userId);
    }

    @PostMapping("/save")
    public Request addRequest(@RequestBody Request request){
        return requestService.saveRequest(request);
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Boolean> deleteRequest(@PathVariable int id)
            throws ResourceNotFoundException {
        Request request = requestService.findRequestById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found for this id :: " + id));

        requestService.deleteRequest(request);
        Map<String, Boolean> response = new HashMap<>();
        response.put("request deleted", Boolean.TRUE);
        return response;
    }




    @PostMapping("/friendship")
    public ResponseEntity<String> postGreeting(@RequestBody Map<String,String> req) {
        //System.out.println("request: " + req.get("request"));
        String friendhipRequest = req.get("request");
        String[] requestDetails = friendhipRequest.split("\\s+");
        // Checking the email address if exists in our database.
        User user = userService.findUserByEmail(requestDetails[4]);
        String foreignEmail = requestDetails[1];


        if(user!= null){
            // create request in DB
            Request rq = new Request(user, foreignEmail);

            if(requestService.findRequestByUserAndForeignEmail(user,foreignEmail)==null){
                requestService.saveRequest(rq);
                return ResponseEntity.ok("TRUE Request created in server B."+req.get("request")+" On "+new Date());
            }


        }
            return ResponseEntity.ok("User does not exist in Server B." +req.get("request"));



        //System.out.println(friendhipRequest);
        //newRequests.put(req.get("request"),"pending");

        //return ResponseEntity.ok("Request received from "+req.get("request")+" On "+new Date());

    }
}
