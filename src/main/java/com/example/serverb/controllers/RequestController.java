package com.example.serverb.controllers;

import com.example.serverb.entities.Request;
import com.example.serverb.services.RequestService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/requests")
public class RequestController {

    RequestService requestService;

    public RequestController(RequestService requestService){
        this.requestService = requestService;
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


}
