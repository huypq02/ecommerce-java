package com.cybersoft.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="user_info_id")
    private int userInfoId;
    @Column(name = "role_id")
    private int roleId;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;


}
