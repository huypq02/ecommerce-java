package com.cybersoft.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name="cart")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "cartDetail")
    private List<CartDetailEntity> cartDetailEntityList;


}
