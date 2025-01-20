package com.cybersoft.uniclub.controller;

import com.cybersoft.uniclub.request.InsertProductionRequest;
import com.cybersoft.uniclub.service.FileService;
import com.cybersoft.uniclub.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/product")
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
