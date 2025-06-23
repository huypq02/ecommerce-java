package com.cybersoft.ecommerce.dto;

import com.cybersoft.ecommerce.entity.RoleEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class UserDto {
    private int id;
    private UserInfoDto userInfo;
    private RoleEntity role;
    private String oauthId;
    private String email;
    private String password;
}
