
package com.amazkart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.amazkart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
