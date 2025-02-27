package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.request.OrderRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface OrderService {
    void addOrder(OrderRequest orderRequest);
    List<OrderRequest> getAllOrder();
}
