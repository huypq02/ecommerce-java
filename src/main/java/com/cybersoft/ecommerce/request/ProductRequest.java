package com.cybersoft.ecommerce.request;

import lombok.Data;

import java.util.List;

@Data
public class ProductRequest {
    private String name;
    private String note;
    private Double rate;
    private List<ProductDetailRequest> productDetailRequests;
}
