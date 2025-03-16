package com.amazkart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazkart.dto.AddressDto;
import com.amazkart.service.AddressService;

@RestController
@RequestMapping("api/user/addresses")
public class AddressController {

	private Logger logger = LoggerFactory.getLogger(AddressController.class);
	@Autowired
	private AddressService addressService;

	@PostMapping
	public ResponseEntity<AddressDto> createAddress(@RequestBody AddressDto addressDto) {
		return ResponseEntity.ok(addressService.createAddress(addressDto));
	}

	@PostMapping("/{id}")
	public ResponseEntity<AddressDto> addAddressToUser(@PathVariable Long id, @RequestBody AddressDto addressDto) {
		return ResponseEntity.ok(addressService.addAddressToUser(id, addressDto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<AddressDto> updateAddress(@PathVariable Long id, @RequestBody AddressDto addressDto) {
		addressDto.setId(id);
		return ResponseEntity.ok(addressService.updateAddress(addressDto));
	}

	@GetMapping("/{id}")
	public ResponseEntity<AddressDto> getAddress(@PathVariable Long id) {
		return ResponseEntity.ok(addressService.getAddress(id));
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<AddressDto> getAddressByUserId(@PathVariable Long userId) {
		return ResponseEntity.ok(addressService.getAddressByUserId(userId));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAddress(@PathVariable Long id) {
		addressService.deleteAddress(id);
		return ResponseEntity.ok("Address deleted successfully...");
	}
}
