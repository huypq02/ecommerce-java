package com.cybersoft.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date")
    private Date date;
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "status")
    private String status;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "province")
    private String province;
    @Column(name = "apt")
    private String apt;
    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "shipping_fee")
    private double shippingFee;
    @Column(name = "tax")
    private double tax;
    @Column(name = "discount")
    private double discount;
    @Column(name = "total")
    private double total;

    @OneToMany(mappedBy = "order")
    private List<OrderDetailEntity> orderDetailList;

    @OneToMany(mappedBy = "order")
    private List<OrderStatusHistoryEntity> orderStatusHistoryList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
