package com.amazkart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazkart.dto.RoleDto;
import com.amazkart.exception.RoleAlreadyExistsException;
import com.amazkart.service.RoleService;

@RestController
@RequestMapping("api/super/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping("/create")
	public ResponseEntity<?> createRole(@RequestBody RoleDto roleDto) throws RoleAlreadyExistsException {

		return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(roleDto));
	}

}
