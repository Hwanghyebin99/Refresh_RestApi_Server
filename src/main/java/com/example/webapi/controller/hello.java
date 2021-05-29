package com.example.webapi.controller;

import com.example.webapi.model.User;
import com.example.webapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class hello {
    @Autowired
    UserService userService;

    @GetMapping("/test")
    public String hello() {
       return "Hello World";
    }

    @RequestMapping(value="/users",method= RequestMethod.GET)
    public String getUser(HttpServletRequest request) {
        List<User> userList = userService.getUser();

        return userList.toString();
    }
}
