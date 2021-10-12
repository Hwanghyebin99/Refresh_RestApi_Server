package com.example.webapi.model;

import lombok.Data;

@Data
public class TbMainPlaceVo {
    /**
     * 이름
     */
    private String name;
    /**
     * 설명
     */
    private String description;
    /**
     * 이미지 경로
     */
    private String imgSrc;
    /**
     * 태그
     */
    private String tag;
    /**
     * 별점
     */
    private float score;
}
