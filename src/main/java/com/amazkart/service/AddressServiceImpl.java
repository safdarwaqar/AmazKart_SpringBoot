package com.amazkart.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazkart.dto.AddressDto;
import com.amazkart.entity.Address;
import com.amazkart.entity.User;
import com.amazkart.repository.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {

	Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public AddressDto createAddress(AddressDto addressDto) {
		Address address = modelMapper.map(addressDto, Address.class);

		Address savedAddress = addressRepository.save(address);
		return modelMapper.map(savedAddress, AddressDto.class);
	}

	@Override
	public void deleteAddress(Long addressId) {
		addressRepository.findById(addressId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid address ID: " + addressId));
		logger.info("Deleting address with ID: " + addressId);
		addressRepository.deleteById(addressId);
	}

	@Override
	public AddressDto updateAddress(AddressDto addressDto) {

		Optional<Address> optionalAddress = addressRepository.findById(addressDto.getId());
		if (optionalAddress.isPresent()) {
			Address address = modelMapper.map(addressDto, Address.class);
			address.setId(addressDto.getId()); // Ensure ID is retained
			Address updatedAddress = addressRepository.save(address);
			return modelMapper.map(updatedAddress, AddressDto.class);
		}
		return null;
	}

	@Override
	public AddressDto getAddress(Long addressId) {
		Optional<Address> optionalAddress = addressRepository.findById(addressId);
		return optionalAddress.map(address -> modelMapper.map(address, AddressDto.class)).orElseThrow(() -> new IllegalArgumentException("Invalid address ID: " + addressId));
	}

	@Override
	public AddressDto getAddressByUserId(Long userId) {
		Optional<Address> optionalAddress = addressRepository.findAddressByUserId(userId);
		return optionalAddress.map(address -> modelMapper.map(address, AddressDto.class)).orElse(null);
	}

	@Override
	public AddressDto addAddressToUser(Long userId, AddressDto addressDto) {
		// Step 1: Fetch the existing user from the database
		User user = userService.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

		// Step 2: Check if the user already has 10 addresses
		long userAddressCount = addressRepository.countByUserId(userId);
		if (userAddressCount >= 10) {
			logger.info("User already has 10 addresses");
			throw new IllegalArgumentException("You are not allowed to add more than 10 addresses");
		}

		// Step 3: Convert AddressDto to Address Entity
		Address address = modelMapper.map(addressDto, Address.class);

		// Step 4: Set the user reference in Address entity
		address.setUser(user);

		// Step 5: Save the address
		Address savedAddress = addressRepository.save(address);

		// Step 6: Convert to DTO and return
		return modelMapper.map(savedAddress, AddressDto.class);
	}

	@Override
	public long getAddressCount(Long userId) {
		return addressRepository.countByUserId(userId);
	}

}
