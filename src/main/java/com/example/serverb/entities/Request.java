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
    private User user; // Sender

    private String receiver;

    private String status = "pending";


    public Request(User user, String receiver){
        this.user = user;
        this.receiver = receiver;
    }

}
