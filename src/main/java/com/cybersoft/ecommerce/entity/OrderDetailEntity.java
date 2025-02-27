package com.cybersoft.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "order_detail")
public class OrderDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "present_unit_price")
    private double presentUnitPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @Column(name = "color")
    private String color;
    @Column(name = "size")
    private String size;

    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    private ProductDetailEntity orderProductDetail;
}
