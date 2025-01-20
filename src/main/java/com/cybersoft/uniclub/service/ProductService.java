package com.cybersoft.uniclub.service;

import com.cybersoft.uniclub.dto.ProductDto;
import com.cybersoft.uniclub.request.InsertProductionRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    void insertProduct(InsertProductionRequest file);
    List<ProductDto> getAllProduct(int pageSize, int pageNumber);
}
