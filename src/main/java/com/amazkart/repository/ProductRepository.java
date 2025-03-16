package com.amazkart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazkart.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
