package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.dto.ProductDto;
import com.cybersoft.ecommerce.request.InsertProductionRequest;
import com.cybersoft.ecommerce.request.ProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    void insertProduct(ProductRequest productRequest, MultipartFile[] files);
    List<ProductDto> getAllProduct(int pageSize, int pageNumber);
}
