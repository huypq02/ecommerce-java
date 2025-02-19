package com.cybersoft.ecommerce.request;
import lombok.Data;

import java.util.Date;

@Data
public class OrderStatusHistoryRequest {
<<<<<<< HEAD
    private String date;
=======
    private Date date;
>>>>>>> 9849228 (feat: order)
    private String status;
}
