package com.cybersoft.ecommerce.request;

import lombok.Data;

@Data
public class AccountRequest {
    private String email;
    private String password;

    private String fullName;
    private String gender;
    private String birthDay;
    private String address;
    private String phone;
    private String description;
}
