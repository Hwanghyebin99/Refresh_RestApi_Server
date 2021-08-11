package com.example.webapi.controller;

import com.example.webapi.BasicRoundController;
import com.example.webapi.FederatedServerImpl;
import com.example.webapi.core.FederatedAveragingStrategy;
import com.example.webapi.core.datasource.*;
import com.example.webapi.core.domain.model.*;
import com.example.webapi.core.domain.repository.ServerRepository;
import com.example.webapi.model.User;
import com.example.webapi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

@RestController
public class HomeController {
    private static FederatedServer federatedServer;

    @Autowired
    UserService userService;

    @Autowired
    FederatedService federatedService;

    public HomeController() throws IOException {
        this.federatedService.startFederatedServer();
    }

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
