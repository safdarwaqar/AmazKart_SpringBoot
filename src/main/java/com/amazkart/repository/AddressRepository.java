
package com.amazkart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.amazkart.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
