package com.cybersoft.ecommerce.request;

import lombok.Data;

@Data
public class OrderDetailRequest {
    private int quantity;
    private double price;
    private String color;
    private String size;
}
