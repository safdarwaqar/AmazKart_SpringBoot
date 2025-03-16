package com.amazkart.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazkart.dto.OrderDto;
import com.amazkart.entity.Order;
import com.amazkart.entity.OrderItem;
import com.amazkart.entity.OrderStatus;
import com.amazkart.entity.Product;
import com.amazkart.entity.User;
import com.amazkart.repository.OrderRepository;
import com.amazkart.repository.ProductRepository;
import com.amazkart.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final ModelMapper modelMapper;

	@Override
	@Transactional
	public OrderDto placeOrder(Long userId, OrderDto orderDto) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		Order order = new Order();
		order.setUser(user);
		order.setOrderDate(new Date());
		order.setStatus(OrderStatus.PENDING);

		// Convert DTO items to entity
		List<OrderItem> orderItems = orderDto.getItems().stream().map(itemDto -> {
//			Product product = productRepository.findById(itemDto.getProductId())
//					.orElseThrow(() -> new RuntimeException("Product not found"));

			Product product = productRepository.findById(itemDto.getProductId())
					.orElse(Product.builder().id(203l).price(100.0).build());
			OrderItem item = new OrderItem();
			item.setOrder(order);
			item.setProduct(product);
			item.setQuantity(itemDto.getQuantity());
			item.setPrice(product.getPrice() * itemDto.getQuantity());
			return item;
		}).collect(Collectors.toList());

		order.setItems(orderItems);
		order.setTotalAmount(orderItems.stream().mapToDouble(OrderItem::getPrice).sum());

		Order savedOrder = orderRepository.save(order);
		return modelMapper.map(savedOrder, OrderDto.class);
	}

	@Override
	public OrderDto getOrderById(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
		return modelMapper.map(order, OrderDto.class);
	}

	@Override
	public List<OrderDto> getOrdersByUserId(Long userId) {
		List<Order> orders = orderRepository.findByUserId(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		return orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<OrderDto> getAllOrders() {
		List<Order> orders = orderRepository.findAll();
		return orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void cancelOrder(Long orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
		order.setStatus(OrderStatus.CANCELED);
		orderRepository.save(order);
	}
}
