package com.cybersoft.ecommerce.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserInfoDto {
    private int id;
    private String fullName;
    private String gender;
    private String birthday;
    private String address;
    private String phone;
    private String description;
}
