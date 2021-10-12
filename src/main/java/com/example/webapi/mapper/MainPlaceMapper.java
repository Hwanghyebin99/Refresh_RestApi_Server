package com.example.webapi.mapper;

import com.example.webapi.model.TbMainPlaceVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MainPlaceMapper {
    List<TbMainPlaceVo> getMainPlaces();
}
