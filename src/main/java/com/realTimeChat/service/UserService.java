package com.realTimeChat.service;

import com.realTimeChat.entity.Status;
import com.realTimeChat.entity.User;
import com.realTimeChat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public void saveUser(User user){
        user.setStatus(Status.ONLINE);
        repository.save(user);

    }

    public void disconnect(User user){
        var storedUser = repository.findById(user.getNickName())
                .orElse(null);
        if (storedUser != null){
            storedUser.setStatus(Status.OFFLINE);
            repository.save(storedUser);
        }

    }

    public List<User> findConnectedUser(){
        return repository.findAllByStatus(Status.ONLINE);
    }

}
