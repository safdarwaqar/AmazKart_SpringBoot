package com.amazkart.service;

import com.razorpay.RazorpayException;

public interface RazorPayPaymentService {

	String createPayment(double amount, String currency, String receipt) throws RazorpayException;

	String capturePayment(String paymentId, double amount);

	String refundPayment(String paymentId, double amount);

	String captureOrder(String orderId, double amount);

	String refundOrder(String orderId, double amount);

	String createDirectPayment(double amount, String currency, String paymentMethod, String paymentDetails);

}
