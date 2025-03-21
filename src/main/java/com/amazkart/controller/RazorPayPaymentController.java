package com.amazkart.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.amazkart.service.RazorPayPaymentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/payment")
@Slf4j
public class RazorPayPaymentController {

	@Autowired
	private RazorPayPaymentService razorpayService;

	@PostMapping("/create-order")
	public String createOrder(@RequestParam double amount, @RequestParam String currency,
			@RequestParam String receipt) {
		try {
			log.info("Initiating request to create order");
			return razorpayService.createPayment(amount, currency, receipt);
		} catch (Exception e) {
			log.error("Exception occurred while creating RazorPay order: {}", e.toString());
			return "Error while creating order: " + e.getMessage();
		}
	}
	
	@PostMapping("/create-direct-payment")
	public String createDirectPayment(@RequestParam double amount) {
		try {
			return razorpayService.createDirectPayment(amount, null, null, null);
		} catch (Exception e) {
			return "Error while creating payment: " + e.getMessage();
		}
	}

	@PostMapping("/capture-payment")
	public String capturePayment(@RequestParam String paymentId, @RequestParam double amount) {
		try {
			return razorpayService.capturePayment(paymentId, amount);
		} catch (Exception e) {
			return "Error while creating order: " + e.getMessage();
		}
	}
}
