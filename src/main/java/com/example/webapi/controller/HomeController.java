package com.example.webapi.controller;

import com.example.webapi.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
public class HomeController {
    public FederatedService federatedService = new FederatedService();

    public HomeController() throws IOException {
        federatedService.startFederatedServer();
    }

    @GetMapping("/test")
    public String hello() {
       return "Hello World";
    }

}
