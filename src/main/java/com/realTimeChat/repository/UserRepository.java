package com.realTimeChat.repository;

import com.realTimeChat.entity.Status;
import com.realTimeChat.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    List<User> findAllByStatus(Status status);
}
