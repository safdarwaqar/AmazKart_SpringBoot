package com.amazkart.controller;

import com.amazkart.dto.OrderDto;
import com.amazkart.service.OrderService;
import com.amazkart.utility.JwtDetailExtractor;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

	@Autowired
	private final OrderService orderService;

	@PostMapping("/place-order")
	public ResponseEntity<OrderDto> placeOrder(@RequestBody OrderDto orderDto) {

		return ResponseEntity
				.ok(orderService.placeOrder(JwtDetailExtractor.getUserDetailsFromSpring().getUserId(), orderDto));
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
		return ResponseEntity.ok(orderService.getOrderById(orderId));
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<OrderDto>> getOrdersByUser() {
		return ResponseEntity
				.ok(orderService.getOrdersByUserId(JwtDetailExtractor.getUserDetailsFromSpring().getUserId()));
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
