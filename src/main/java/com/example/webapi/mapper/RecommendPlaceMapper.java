package com.example.webapi.mapper;

import com.example.webapi.model.TbRecommendPlaceVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RecommendPlaceMapper {
    List<TbRecommendPlaceVo> getPlaces(float distance, float x, float y);
}
