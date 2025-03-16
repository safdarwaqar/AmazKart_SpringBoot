
package com.amazkart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.amazkart.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	Optional<Order> findById(Long id);
	Optional<List<Order>> findByUserId(Long userId);
}
