package com.example.serverb.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class FriendList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;  // homeUser

    private int foreignUserId; // foreignUser
    private String foreignUserHost;  // IP of where foreignUser comes from


    public FriendList(User user, int foreignUserId, String foreignUserHost){
        this.user = user;
        this.foreignUserId = foreignUserId;
        this.foreignUserHost = foreignUserHost;

    }

}

