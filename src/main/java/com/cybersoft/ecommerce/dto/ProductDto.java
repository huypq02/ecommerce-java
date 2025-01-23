package com.cybersoft.ecommerce.dto;

import lombok.Data;

@Data
public class ProductDto {
    private int id;
    private String name;
    private String note;
    private double rate;
    private String color;
    private String size;
    private int quantity;
    private double price;
    private String urlName;
}
