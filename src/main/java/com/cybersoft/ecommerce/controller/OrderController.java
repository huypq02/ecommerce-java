package com.cybersoft.ecommerce.controller;

import com.cybersoft.ecommerce.entity.OrderEntity;
import com.cybersoft.ecommerce.request.OrderRequest;
import com.cybersoft.ecommerce.response.BaseResponse;
import com.cybersoft.ecommerce.service.CartService;
import com.cybersoft.ecommerce.service.OrderService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    CartService cartService;

    @PostMapping()
    public ResponseEntity<?> order(@RequestBody OrderRequest order) {
        BaseResponse baseResponse = new BaseResponse();
        try{
            orderService.addOrder(order);
            cartService.deleteCartByUserId(); // Delete cart after order
        } catch (Exception e) {
            baseResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            baseResponse.setData("Order failed");
            return ResponseEntity.ok(baseResponse);
        }

        baseResponse.setCode(200);
        baseResponse.setData("Order success");
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping()
    public ResponseEntity<?> getAllOrder() {
        return ResponseEntity.ok(orderService.getAllOrder());
    }
}
