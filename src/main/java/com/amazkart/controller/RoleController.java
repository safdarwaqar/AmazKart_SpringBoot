package com.amazkart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazkart.dto.RoleDto;
import com.amazkart.entity.Role;
import com.amazkart.repository.RoleRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleRepository roleRepository;

	@PostMapping("/create")
	public Role createRole(@RequestBody RoleDto role) {
		
		if (roleRepository.findByName(role.getName())) {
			throw new RuntimeException("Role already exists");
		}

		return null;
	}

}
