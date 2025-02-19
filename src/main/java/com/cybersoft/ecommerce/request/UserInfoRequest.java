package com.cybersoft.ecommerce.request;


import lombok.Data;

@Data
public class UserInfoRequest {
    private String fullName;
    private String gender;
    private String birthday;
    private String address;
    private String phone;
    private String description;
}
