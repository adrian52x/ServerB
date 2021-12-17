package com.example.serverb.repos;

import com.example.serverb.entities.FriendList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendListRepository extends CrudRepository<FriendList,Integer> {
    List<FriendList> findFriendListByUser_Id(int userId);
    FriendList findFriendListByUserEmailAndForeignUserEmail(String userEmail,String foreignUserEmail);
}
