package com.amazkart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.amazkart.entity.Address;
import com.amazkart.entity.User;
import com.amazkart.repository.AddressRepository;
import com.amazkart.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User saveUser(User user) {
	    // Encode the password before saving the user
	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    
	    // Save the user entity
	    User savedUser = userRepository.save(user);

	    // Ensure that address is set properly before saving
	    if (!savedUser.getAddresses().isEmpty()) {
	        Address address = savedUser.getAddresses().stream().findFirst().get(); // Get the 0th index
	        address.setUser(savedUser); // Link the saved user to the address
	        
	        // Save the address entity
	        addressRepository.save(address);
	    }
	    
	    return savedUser; // Return the saved user
	}

}
