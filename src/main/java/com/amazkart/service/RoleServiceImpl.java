package com.amazkart.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazkart.dto.RoleDto;
import com.amazkart.dto.RolesEnum;
import com.amazkart.entity.Role;
import com.amazkart.exception.RoleAlreadyExistsException;
import com.amazkart.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	private Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public RoleDto createRole(RoleDto roleDto) throws RoleAlreadyExistsException {
		String roleName = roleDto.getRoleName().toUpperCase().startsWith("ROLE_") ? roleDto.getRoleName().toUpperCase()
				: "ROLE_" + roleDto.getRoleName().toUpperCase();

		Optional<Role> byName = roleRepository.findByName(roleName);
		if (byName.isPresent()) {
			throw new RoleAlreadyExistsException("Role already exists");
		}

		RolesEnum valueOf;
		try {
			valueOf = RolesEnum.valueOf(roleName);
		} catch (IllegalArgumentException e) {
			logger.error(e.toString());
			throw new IllegalArgumentException("Invalid role name from catch block: " + roleDto.getRoleName());
//			return ResponseEntity.badRequest().body("Invalid role name: " + roleDto.getRoleName());
		}

		Role role = new Role();
		role.setName(valueOf.name());
		Role savedRole = roleRepository.save(role);

		return modelMapper.map(savedRole, RoleDto.class);
	}

	@Override
	public void deleteRole(Long roleId) {
		// TODO Auto-generated method stub

	}

}
