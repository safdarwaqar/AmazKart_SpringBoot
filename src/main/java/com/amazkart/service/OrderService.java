package com.amazkart.service;

import com.amazkart.dto.OrderDto;
import java.util.List;

public interface OrderService {
    OrderDto placeOrder(Long userId, OrderDto orderDto);
    OrderDto getOrderById(Long orderId);
    List<OrderDto> getOrdersByUserId(Long userId);
    List<OrderDto> getAllOrders(); // Admin use case
    void cancelOrder(Long orderId);
}
