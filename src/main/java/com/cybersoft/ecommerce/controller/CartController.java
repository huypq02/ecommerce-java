package com.cybersoft.ecommerce.controller;

import com.cybersoft.ecommerce.request.CartRequest;
import com.cybersoft.ecommerce.response.BaseResponse;
import com.cybersoft.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartRequest cartRequest) {
        BaseResponse baseResponse = new BaseResponse();
        String response =  cartService.addToCart(cartRequest);
        baseResponse.setCode(200);
        baseResponse.setMessage("Succesfully");
        baseResponse.setData(response);
        return ResponseEntity.ok(baseResponse);
    }
}
