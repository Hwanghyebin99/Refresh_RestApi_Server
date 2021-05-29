package com.example.webapi.service;

import com.example.webapi.mapper.UserMapper;
import com.example.webapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    public UserMapper mapper;

    public List<User> getUser() {
        return mapper.getUser();
    }

}
