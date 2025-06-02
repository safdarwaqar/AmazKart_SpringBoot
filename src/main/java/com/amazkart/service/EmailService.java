package com.amazkart.service;

public interface EmailService {
	
	void sendEmail(String to, String from, String subject, String Body);

}
