package com.cybersoft.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "user_info")
public class UserInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    private String fullName;
    @Column(name = "gender")
    private String gender;
    @Column(name = "birthday")
    private String birthday;
    @Column(name = "address")
    private String address;
    @Column(name = "phone")
    private String phone;
    @Column(name = "description")
    private String description;
    @Column(name="image")
    private String image;
}
