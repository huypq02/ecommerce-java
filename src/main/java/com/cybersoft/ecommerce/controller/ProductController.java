package com.cybersoft.ecommerce.controller;

import com.cybersoft.ecommerce.request.InsertProductionRequest;
import com.cybersoft.ecommerce.response.BaseResponse;
import com.cybersoft.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public BaseResponse getAllProduct(@RequestParam int pageSize, @RequestParam int pageNumber){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(200);
        baseResponse.setMessage("Success");
        baseResponse.setData(productService.getAllProduct(pageSize,pageNumber));
        return baseResponse;
    }
}
