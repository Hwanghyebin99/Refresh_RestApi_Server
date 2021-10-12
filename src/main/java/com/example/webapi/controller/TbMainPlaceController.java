package com.example.webapi.controller;

import com.example.webapi.model.TbMainPlaceVo;
import com.example.webapi.service.MainPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/api")
public class TbMainPlaceController {
    @Autowired
    MainPlaceService service;

    @RequestMapping(value="/getMainPlaces", method = RequestMethod.GET)
    public List<TbMainPlaceVo> getMainPlaces() {
        return service.getMainPlaces();
    }
}
