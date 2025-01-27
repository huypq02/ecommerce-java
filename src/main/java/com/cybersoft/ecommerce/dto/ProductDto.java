package com.cybersoft.ecommerce.dto;

import lombok.Data;

@Data
public class ProductDto {
    private int id;
    private String name;
    private String note;
    private double rate;
}
