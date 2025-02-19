package com.cybersoft.ecommerce.request;
import lombok.Data;

import java.util.Date;

@Data
public class OrderStatusHistoryRequest {
    private String date;
    private String status;
}
