package com.example.serverb.repos;

import com.example.serverb.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    User findUserById(int id);
    User findUserByEmail(String email);

}