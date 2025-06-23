package com.cybersoft.ecommerce.controller;

import com.cybersoft.ecommerce.dto.CartDTO;
import com.cybersoft.ecommerce.request.CartRequest;
import com.cybersoft.ecommerce.response.BaseResponse;
import com.cybersoft.ecommerce.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartRequest cartRequest) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            cartService.addToCart(cartRequest);
            baseResponse.setCode(200);
            baseResponse.setMessage("Successfully added to cart");
            return ResponseEntity.ok(baseResponse);
        } catch (Exception e) {
            baseResponse.setCode(500);
            baseResponse.setMessage("Failed to add to cart: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllCartByUserId() {
        BaseResponse baseResponse = new BaseResponse();

        List<CartDTO> cartDTOList = cartService.getAllCarts();

        baseResponse.setCode(200);
        baseResponse.setMessage("Successfully");
        baseResponse.setData(cartDTOList);

        return ResponseEntity.ok(baseResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable int id) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            cartService.deleteCart(id);
            baseResponse.setCode(200);
            baseResponse.setMessage("Successfully deleted cart");
            return ResponseEntity.ok(baseResponse);
        } catch (Exception e) {
            baseResponse.setCode(500);
            baseResponse.setMessage("Failed to delete cart: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCartByUserId() {
        try {
            boolean isDeleted = cartService.deleteCartByUserId();
            if (isDeleted) {
                return ResponseEntity.ok("Cart deleted successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found!");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
