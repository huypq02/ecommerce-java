package com.cybersoft.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name= "cart_detail")
public class CartDetailEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int id;
    @Column(name="quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name ="cart_id")
    private CartEntity cartDetailID;

    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    private ProductDetailEntity cartProductDetailID;

}
