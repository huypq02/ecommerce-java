package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.request.CartRequest;

public interface CartService {
    String addToCart(CartRequest cartRequest);
}
