package com.cybersoft.ecommerce.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductDto {
    private int id;
    private String name;
    private String note;
    private double rate;
    private List<ProductDetailDto> productDetailDtoList;

}
