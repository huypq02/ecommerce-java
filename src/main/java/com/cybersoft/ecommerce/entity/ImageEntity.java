package com.cybersoft.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "image")
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "url")
    private String urlName;
    @Column(name = "all_text")
    private String all_text;

    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    private ProductDetailEntity productDetailEntity;


}
