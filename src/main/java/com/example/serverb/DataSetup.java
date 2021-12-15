package com.example.serverb;

import com.example.serverb.entities.FriendList;
import com.example.serverb.entities.Request;
import com.example.serverb.entities.User;
import com.example.serverb.repos.FriendListRepository;
import com.example.serverb.repos.RequestRepository;
import com.example.serverb.repos.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSetup implements CommandLineRunner {

    UserRepository userRepository;
    RequestRepository requestRepository;
    FriendListRepository friendListRepository;

    public DataSetup(UserRepository userRepository, RequestRepository requestRepository, FriendListRepository friendListRepository){
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
        this.friendListRepository = friendListRepository;
    }



    @Override
    public void run(String... args) throws Exception {

        User u1 = userRepository.save(new User("Eva","Pedersen","e@gmail.com"));
        User u2 = userRepository.save(new User("Frank","Luft","f@gmail.com"));
        User u3 = userRepository.save(new User("Gigolo","Celio","g@gmail.com"));
        User u4 = userRepository.save(new User("Henrik","Vandam","h@gmail.com"));

        //requestRepository.save(new Request(u1,"g@gmail.com"));
        //requestRepository.save(new Request(u1,"h@gmail.com"));






    }
}