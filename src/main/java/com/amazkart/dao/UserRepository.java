package com.amazkart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amazkart.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
