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
    private String userEmail; //homeUserEmail

    private int foreignUserId; // foreignUserId
    private String foreignUserEmail; // foreignUserEmail
    private String foreignUserHost;  // IP of where foreignUser comes from


    public FriendList(User user, String userEmail, int foreignUserId, String foreignUserEmail, String foreignUserHost) {
        this.user = user;
        this.userEmail = userEmail;
        this.foreignUserId = foreignUserId;
        this.foreignUserEmail = foreignUserEmail;
        this.foreignUserHost = foreignUserHost;
    }

}

