package com.amazkart.service;

import com.amazkart.dto.RoleDto;
import com.amazkart.exception.RoleAlreadyExistsException;

public interface RoleService {

	RoleDto createRole(RoleDto roleDto) throws RoleAlreadyExistsException;

	void deleteRole(Long roleId);

}
