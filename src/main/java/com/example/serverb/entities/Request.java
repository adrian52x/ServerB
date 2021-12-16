package com.example.serverb.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user; // homeUser
    private String userEmail; // homeUserEmail

    private int foreignUserId; //foreignUser Id
    private String foreignUserEmail; //foreignUser email

    private String status = "pending";


    public Request(User user, String userEmail, int foreignUserId, String foreignUserEmail){
        this.user = user;
        this.userEmail = userEmail;
        this.foreignUserId = foreignUserId;
        this.foreignUserEmail = foreignUserEmail;
    }

}
