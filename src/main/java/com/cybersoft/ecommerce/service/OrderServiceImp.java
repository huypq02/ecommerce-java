package com.cybersoft.ecommerce.service;

import com.cybersoft.ecommerce.entity.*;
import com.cybersoft.ecommerce.repository.*;
import com.cybersoft.ecommerce.request.OrderDetailRequest;
import com.cybersoft.ecommerce.request.OrderRequest;
import com.cybersoft.ecommerce.request.OrderStatusHistoryRequest;
import com.cybersoft.ecommerce.utils.JwtHelper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.cybersoft.ecommerce.utils.DateUtil.convertStringToDate;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderStatusHistoryRepository orderStatusHistoryRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    @Transactional
    public void addOrder(OrderRequest orderRequest) {

        try {
            // Step 1: Get user info from token by parsing JWT
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                throw new RuntimeException("Missing or invalid Authorization header");
            }
            String token = authorizationHeader.substring(7);
            Claims claims = jwtHelper.getClaims(token);
            int userId = claims.get("userId", Integer.class);

            // Step 2: Save this user info to order
            OrderEntity order = new OrderEntity();
            order.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
            // convert string to date
            Date now = new Date();
            Date orderDate = convertStringToDate(orderRequest.getDate());
            order.setDate(orderDate != null ? orderDate : now);

            order.setPaymentMethod(orderRequest.getPaymentMethod());
            order.setStatus(orderRequest.getStatus()); // TODO: auto generate status
            order.setFullName(orderRequest.getFullName());
            order.setPhone(orderRequest.getPhone());
            order.setAddress(orderRequest.getAddress());
            order.setPostalCode(orderRequest.getPostalCode());
            order.setCity(orderRequest.getCity());
            order.setCountry(orderRequest.getCountry());
            order.setProvince(orderRequest.getProvince());
            order.setApt(orderRequest.getApt());
            order.setTransactionId(orderRequest.getTransactionId());
            order.setShippingFee(orderRequest.getShippingFee());
            order.setTax(orderRequest.getTax());
            order.setDiscount(orderRequest.getDiscount());
            order.setTotal(orderRequest.getTotal());
            // save order
            orderRepository.save(order);

            // add product to order
            for (OrderDetailRequest orderDetailRequest : orderRequest.getOrderDetail()) {
                OrderDetailEntity orderDetail = new OrderDetailEntity();
                orderDetail.setOrder(order);
                orderDetail.setQuantity(orderDetailRequest.getQuantity());
                orderDetail.setPresentUnitPrice(orderDetailRequest.getPresentUnitPrice());
                orderDetail.setColor(orderDetailRequest.getColor());
                orderDetail.setSize(orderDetailRequest.getSize());
                orderDetail.setOrderProductDetail(productDetailRepository.findById(orderDetailRequest.getProductDetailId()).orElseThrow(() -> new RuntimeException("Product detail not found")));
                orderDetailRepository.save(orderDetail);
            }

            for (OrderStatusHistoryRequest orderStatusHistoryRequest : orderRequest.getOrderStatusHistory()) {
                OrderStatusHistoryEntity orderStatusHistory = new OrderStatusHistoryEntity();
                orderStatusHistory.setOrder(order);
                orderStatusHistory.setStatus(orderStatusHistoryRequest.getStatus());
                // convert string to date
                Date statusDate = convertStringToDate(orderStatusHistoryRequest.getDate());
                orderStatusHistory.setDate(statusDate != null ? statusDate : now);
                orderStatusHistoryRepository.save(orderStatusHistory);
            }

        }
        catch (Exception e) {
            throw new RuntimeException("Order failed");
        }
    }
}
