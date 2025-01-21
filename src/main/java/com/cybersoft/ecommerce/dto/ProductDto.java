package com.cybersoft.ecommerce.dto;

import lombok.Data;

@Data
public class ProductDto {
    private int id;
    private String name;
    private String description;
    private double price;
    private String information;
    private String createDate;
    private int brandId;
    private String brandName;
    private int quantity;
    private String color;
    private String size;
    private String image;
}
