package com.example.webapi.model;

import lombok.Builder;
import lombok.Data;

@Data
public class User {
    private String UserName;
    private String UserPwd;
    private String UserId;
}
