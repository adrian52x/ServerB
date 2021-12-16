package com.example.serverb.repos;

import com.example.serverb.entities.Request;
import com.example.serverb.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends CrudRepository<Request,Integer> {
    List<Request> findRequestByUser_Id(int userId);
    Optional<Request> findRequestById(int id);
    Request deleteRequestById(int id);
    Request findRequestByUserAndForeignUserEmail(User user, String foreignUserEmail);
}
