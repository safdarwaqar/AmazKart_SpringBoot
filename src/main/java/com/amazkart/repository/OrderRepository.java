
package com.amazkart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.amazkart.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
