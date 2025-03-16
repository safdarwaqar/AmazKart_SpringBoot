package com.amazkart.dto;

import com.amazkart.entity.User;

import lombok.Data;

@Data
public class AddressDto {

	private Long id;

	private String street;

	private String city;

	private String state;

	private String country;

	private String zipCode;

	private User user;

}
