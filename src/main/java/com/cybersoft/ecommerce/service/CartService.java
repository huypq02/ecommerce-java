package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.dto.CartDTO;
import com.cybersoft.ecommerce.request.CartRequest;

import java.util.List;

public interface CartService {
    void addToCart(CartRequest cartRequest);
    void deleteCart(int id);
    List<CartDTO> getAllCarts();
    boolean deleteCartByUserId();
}
