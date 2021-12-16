package com.example.serverb.controllers;

import com.example.serverb.entities.User;
import com.example.serverb.services.UserService;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/all")
    public Iterable<User> getAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/id/{id}")
    public User getUserById(@PathVariable int id){
        return userService.findUserById(id);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email){
        return userService.findUserByEmail(email);
    }

    @PostMapping("/adduser")
    public User addUser(@RequestBody User user){
        return userService.saveUser(user);
    }

}
