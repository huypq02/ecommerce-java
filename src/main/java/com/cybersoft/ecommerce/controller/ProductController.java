package com.cybersoft.ecommerce.controller;

import com.cybersoft.ecommerce.request.InsertProductionRequest;
import com.cybersoft.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collection")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping()
    public ResponseEntity<?> insertProduct(InsertProductionRequest request){
        productService.insertProduct(request);
        return ResponseEntity.ok("Insert product successfully");
    }

    @GetMapping()
    public ResponseEntity<?> getAllProduct(@RequestParam int pageSize, @RequestParam int pageNumber){

        return ResponseEntity.ok(productService.getAllProduct(pageSize, pageNumber));
    }
}
