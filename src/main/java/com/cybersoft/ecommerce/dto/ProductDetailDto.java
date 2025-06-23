package com.cybersoft.ecommerce.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductDetailDto {
    private int id;
    private String color;
    private String size;
    private int quantity;
    private double price;
    private List<ImageDto> imageDtoList;
}
