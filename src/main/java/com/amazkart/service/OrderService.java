package com.amazkart.service;

import java.util.List;

import com.amazkart.dto.OrderDto;

public interface OrderService {
	OrderDto placeOrder(Long userId, OrderDto orderDto);

	OrderDto getOrderById(Long orderId);

	List<OrderDto> getOrdersByUserId(Long userId);

	List<OrderDto> getAllOrders(); // Admin use case

	void cancelOrder(Long orderId);
}
