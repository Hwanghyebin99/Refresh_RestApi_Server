package com.example.webapi.controller;

import com.example.webapi.model.TbRecommendPlaceVo;
import com.example.webapi.service.RecommendPlaceService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/api")
public class TbRecommendPlaceController {
    @Autowired
    RecommendPlaceService service;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "meters", dataType = "int", value = "반경(meter)"),
            @ApiImplicitParam(name = "x", dataType = "float", value = "위도"),
            @ApiImplicitParam(name = "y", dataType = "float", value = "경도")
    })
    @RequestMapping(value="/getPlaces",method= RequestMethod.GET)
    public List<TbRecommendPlaceVo> getPlaces(@RequestParam int meters, @RequestParam float x, @RequestParam float y) {
        return service.getPlaces((float) (meters*0.001), x, y);
    }

}
