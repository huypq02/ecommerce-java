package com.cybersoft.ecommerce.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class InsertProductionRequest {
    private MultipartFile file;
    private String name;
    private double price;
    private int brandId;
    private int colorId;
    private int sizeId;
    private int quantity;
}
