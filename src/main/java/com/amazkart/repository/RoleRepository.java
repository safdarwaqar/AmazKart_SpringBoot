package com.amazkart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazkart.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	boolean findByName(String name);

}
