package com.amazkart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class GmailSender implements EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String from;

	@Override
	public void sendEmail(String to, String from, String subject, String Body) {
		from=this.from;
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setCc("safdar.waqar@hotmail.com");
		message.setSubject(subject);
		message.setText(Body);
		message.setFrom(from);

		mailSender.send(message);
		System.out.println("📧 Email sent successfully to " + to);

	}

}
