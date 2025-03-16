package com.amazkart.service;

import com.amazkart.dto.AddressDto;

public interface AddressService {

	AddressDto createAddress(AddressDto addressDto);

	AddressDto updateAddress(AddressDto addressDto);

	AddressDto getAddress(Long addressId);

	AddressDto getAddressByUserId(Long userId);
	
	void deleteAddress(Long addressId);
	
	long getAddressCount(Long userId);
	
	AddressDto addAddressToUser(Long userId, AddressDto addressDto);

}
