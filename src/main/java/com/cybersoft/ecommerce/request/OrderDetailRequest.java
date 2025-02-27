package com.cybersoft.ecommerce.request;

import lombok.Data;

@Data
public class OrderDetailRequest {
    private int productDetailId;
    private int quantity;
    private double presentUnitPrice;
    private String color;
    private String size;
}
