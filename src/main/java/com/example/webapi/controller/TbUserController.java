package com.example.webapi.controller;

import com.example.webapi.model.User;
import com.example.webapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TbUserController {
    @Autowired
    UserService userService;

    @RequestMapping(value="/users",method= RequestMethod.GET)
    public String getUser(HttpServletRequest request) {
        List<User> userList = userService.getUser();

        return userList.toString();
    }
}
