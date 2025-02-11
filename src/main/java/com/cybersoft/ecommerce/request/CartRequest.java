package com.cybersoft.ecommerce.request;

import lombok.Data;

@Data
public class CartRequest {
    private int cartID;
    private int productID;
    private int quantity;
}
