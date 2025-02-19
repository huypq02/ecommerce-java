package com.cybersoft.ecommerce.controller;

import com.cybersoft.ecommerce.entity.OrderEntity;
import com.cybersoft.ecommerce.request.OrderRequest;
import com.cybersoft.ecommerce.service.OrderService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping()
    public ResponseEntity<?> order(@RequestBody OrderRequest order) {
        try{

            orderService.addOrder(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Order failed");
        }

        return ResponseEntity.ok("Order success");
    }
}
