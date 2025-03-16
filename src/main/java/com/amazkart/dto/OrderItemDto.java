package com.amazkart.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
	private Long productId;
	private String productName;
	private int quantity;
	private Double price;
}
