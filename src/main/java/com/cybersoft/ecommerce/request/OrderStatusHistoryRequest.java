package com.cybersoft.ecommerce.request;
import lombok.Data;

import java.util.Date;

@Data
public class OrderStatusHistoryRequest {
    private Date date;
    private String status;
}
