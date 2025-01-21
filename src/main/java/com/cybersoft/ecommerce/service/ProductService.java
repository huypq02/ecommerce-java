package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.dto.ProductDto;
import com.cybersoft.ecommerce.request.InsertProductionRequest;

import java.util.List;

public interface ProductService {
    void insertProduct(InsertProductionRequest file);
    List<ProductDto> getAllProduct(int pageSize, int pageNumber);
}
