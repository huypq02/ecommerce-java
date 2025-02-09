package com.cybersoft.ecommerce.request;

import lombok.Data;

import java.util.List;

@Data
public class ProductDetailRequest {
    private String color;
    private String size;
    private int quantityStock;
    private double price;
    private List<ImageRequest> images;

}
