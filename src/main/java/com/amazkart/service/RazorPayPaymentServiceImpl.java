package com.amazkart.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class RazorPayPaymentServiceImpl implements RazorPayPaymentService {

	private Logger log = LoggerFactory.getLogger(RazorPayPaymentServiceImpl.class);

	private final String RAZORPAY_KEY_ID;
	private final String RAZORPAY_SECRET;
	private final RazorpayClient razorpayClient;

	public RazorPayPaymentServiceImpl(@Value("${razorpay.key.id}") String razorpayKeyId,
			@Value("${razorpay.key.secret}") String razorpaySecret) throws RazorpayException {
		this.RAZORPAY_KEY_ID = razorpayKeyId;
		this.RAZORPAY_SECRET = razorpaySecret;
		this.razorpayClient = new RazorpayClient(razorpayKeyId, razorpaySecret);
	}

	@Override
	public String createPayment(double amount, String currency, String receipt) throws RazorpayException {

		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", amount * 100); // Amount in paise
		orderRequest.put("currency", currency);
		orderRequest.put("receipt", receipt);

		Order order = razorpayClient.orders.create(orderRequest);
		log.info("Order created: {}", order.toString());
		return order.toString();
	}

	@Override
	public String createDirectPayment(double amount, String currency, String paymentMethod, String paymentDetails) {
		try {

			JSONObject paymentRequest = new JSONObject();
			paymentRequest.put("amount", 100000); // Amount in paise
			paymentRequest.put("currency", "INR");
			paymentRequest.put("method", "upi"); // e.g., "card", "upi", etc.
			paymentRequest.put("payment_details", "9563242038@apl"); // Card details, UPI ID, etc.

			Payment payment = razorpayClient.payments.createJsonPayment(paymentRequest);
			log.info("Payment created successfully: {}", payment.toString());
			System.out.println("Payment created successfully: " + payment.toString());
			return payment.toString();

		} catch (RazorpayException e) {
			System.err.println("Error while creating payment: " + e.getMessage());
			log.error("Error while creating payment: {}", e.getMessage());
			throw new RuntimeException("Failed to create payment", e);
		}
	}

	@Override
	public String capturePayment(String paymentId, double amount) {
		try {
			JSONObject captureRequest = new JSONObject();
			captureRequest.put("amount", amount); // Amount in paise (same as in order)

			Payment paymenFetch = razorpayClient.payments.fetch(paymentId);
			log.info("Payment Status: {}", paymenFetch.get("status").toString());

			// Capture the payment using RazorpayClient
			Payment payment = razorpayClient.payments.capture(paymentId, captureRequest);
			log.info("Payment successfully captured: {}", payment.toString());
			return payment.toString();
		} catch (RazorpayException e) {
			log.error("Error while capturing payment: {}", e.getMessage());
			System.err.println("Error while capturing payment: " + e.getMessage());
			throw new RuntimeException("Failed to capture payment", e);
		}
	}

	@Override
	public String refundPayment(String paymentId, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String captureOrder(String orderId, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String refundOrder(String orderId, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

}
