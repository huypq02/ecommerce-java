package com.cybersoft.ecommerce.request;

import com.cybersoft.ecommerce.entity.OrderStatusHistoryEntity;
import com.cybersoft.ecommerce.entity.UserInfoEntity;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private int id;
    private List<OrderDetailRequest> orderDetail;
    private List<OrderStatusHistoryRequest> orderStatusHistory;
    private String fullName;
    private String phone;
    private String address;
    private String postalCode;
    private String city;
    private String country;
    private String province;
    private String apt;
    private String transactionId;
    private String date;
    private String paymentMethod;
    private String status;
    private double shippingFee;
    private double tax;
    private double discount;
    private double total;
}
