package com.cybersoft.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "oauth_id")
    private String oauthId;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;
}
