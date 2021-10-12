package com.example.webapi.service;

import com.example.webapi.mapper.MainPlaceMapper;
import com.example.webapi.model.TbMainPlaceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainPlaceService {
    @Autowired
    MainPlaceMapper mapper;

    public List<TbMainPlaceVo> getMainPlaces() {
        return mapper.getMainPlaces();
    }
}
