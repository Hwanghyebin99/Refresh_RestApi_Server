package com.example.webapi.model;

import lombok.Data;

@Data
public class TbRecommendPlaceVo {
    /**
     * id
     */
    private String id;
    private String bemd_nm;
    /**
     * 번지
     */
    private String beonji;
    private String bld_num;
    private String branch_nm;
    private String lclas;
    private String lst_updt_dt;
    private String mlsfc;
    private String poi_nm;
    private String rd_nm;
    private String sgg_nm;
    private String sido_nm;
    private String ri_nm;
    private float x;
    private float y;
}
