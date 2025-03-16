package com.amazkart.controller;

import com.amazkart.dto.OrderDto;
import com.amazkart.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/orders")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/{userId}")
	public ResponseEntity<OrderDto> placeOrder(@PathVariable Long userId, @RequestBody OrderDto orderDto) {
		return ResponseEntity.ok(orderService.placeOrder(userId, orderDto));
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
		return ResponseEntity.ok(orderService.getOrderById(orderId));
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<OrderDto>> getOrdersByUser(@PathVariable Long userId) {
		return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
	}

	@GetMapping
	public ResponseEntity<List<OrderDto>> getAllOrders() {
		return ResponseEntity.ok(orderService.getAllOrders());
	}

	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
		orderService.cancelOrder(orderId);
		return ResponseEntity.ok("Order canceled successfully");
	}
}
