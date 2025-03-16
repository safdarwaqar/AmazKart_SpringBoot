package com.amazkart.dto;

import com.amazkart.entity.OrderStatus;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
	private Long id;
	private Long userId;
	private List<OrderItemDto> items;
	private Double totalAmount;
	private OrderStatus status;
	private Date orderDate;
}
