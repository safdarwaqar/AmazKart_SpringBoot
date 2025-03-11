package com.amazkart.dto;

import java.util.HashSet;
import java.util.Set;

import com.amazkart.entity.Role;

import lombok.Data;

@Data
public class UserRegistration {
	
	private String username;
	private String firstName;
	private String lastName;
	private String phone;
	private String profileImage;
	private Set<Role> roles = new HashSet<>();
	private String password;

}
