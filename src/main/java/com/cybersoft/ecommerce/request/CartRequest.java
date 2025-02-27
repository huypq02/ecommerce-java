package com.cybersoft.ecommerce.request;

import com.cybersoft.ecommerce.entity.UserInfoEntity;
import lombok.Data;

@Data
public class CartRequest {
    private int cartID;
    private int productDetailID;
    private int quantity;
    private int userID;
}
