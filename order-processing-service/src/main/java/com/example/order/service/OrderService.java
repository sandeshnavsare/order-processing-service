package com.example.order.service;

import com.example.order.model.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    public String processOrder(Order order) {
        return "Order placed: " + order.getItem() + " x " + order.getQuantity();
    }
}
