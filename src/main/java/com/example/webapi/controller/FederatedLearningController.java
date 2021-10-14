package com.example.webapi.controller;

import com.example.webapi.model.TbRecommendPlaceVo;
import com.example.webapi.service.FederatedService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping(value="/api")
public class FederatedLearningController {
    public FederatedService federatedService = new FederatedService();

    public FederatedLearningController() throws IOException {
        federatedService.startFederatedServer();
    }

    @RequestMapping(value="/pushGradient",method= RequestMethod.POST)
    public Map pushGradient(@RequestBody InputStream file, @RequestBody int samples){
        return federatedService.pushGradient(file, samples);
    }

    @RequestMapping(value="/getModel", method=RequestMethod.GET)
    public String getFile() {
        return federatedService.getFile();
    }

    @RequestMapping(value="currentRound", method=RequestMethod.GET)
    public String getCurrentRound() {
        return federatedService.getCurrentRound();
    }
}