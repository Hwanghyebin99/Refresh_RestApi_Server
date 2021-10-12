package com.example.webapi.service;

import com.example.webapi.mapper.RecommendPlaceMapper;
import com.example.webapi.model.TbRecommendPlaceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendPlaceService {
    @Autowired
    RecommendPlaceMapper mapper;

    public List<TbRecommendPlaceVo> getPlaces(float distance, float x, float y) {
        return mapper.getPlaces(distance,x, y);
    }
}
