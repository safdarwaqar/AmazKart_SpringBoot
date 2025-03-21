package com.amazkart;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class AmazKartApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(AmazKartApplication.class, args);
		log.info("AmazKart has started successfully...");
		
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
