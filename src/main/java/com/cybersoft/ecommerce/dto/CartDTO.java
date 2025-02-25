package com.cybersoft.ecommerce.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    private String productName;
    private int quantity;
    private String size;
    private double price;
    private List<String> imageUrls;
}
