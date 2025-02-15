
package com.amazkart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.amazkart.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
