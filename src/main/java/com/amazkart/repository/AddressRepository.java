
package com.amazkart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amazkart.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

	Optional<Address> findById(Long userId);

	Optional<Address> findAddressByUserId(Long userId);
	
	long countByUserId(Long userId);

}
