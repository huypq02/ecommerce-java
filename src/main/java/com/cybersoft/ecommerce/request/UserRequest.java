package com.cybersoft.ecommerce.request;

import lombok.Data;

@Data
public class UserRequest {
    private int id;
    private String email;
    private String password;
    private String oauthId;

    private int userInfoId;
    private String fullName;
    private String gender;
    private String birthDay;
    private String address;
    private String phone;
    private String description;

    private int roleId;
    private String role;
    private String roleName;
}
