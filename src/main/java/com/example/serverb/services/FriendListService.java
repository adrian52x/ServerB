package com.example.serverb.services;

import com.example.serverb.entities.FriendList;
import com.example.serverb.repos.FriendListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendListService {

    FriendListRepository friendListRepository;

    //Constructor
    public FriendListService(FriendListRepository friendListRepository){
        this.friendListRepository = friendListRepository;
    }
    /////////////

    public List<FriendList> findFriendListByUserId(int userId){
        return friendListRepository.findFriendListByUser_Id(userId);
    }

    public FriendList saveInFriendList(FriendList friendList){
        return friendListRepository.save(friendList);
    }


}
