package com.cybersoft.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "note")
    private String note;
    private String description;

    @Column(name = "rate")
    private double rate;

    @OneToMany(mappedBy = "productEntity")
    private List<ProductDetailEntity> detailEntityList;
}
