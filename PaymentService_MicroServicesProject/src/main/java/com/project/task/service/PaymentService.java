package com.project.task.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

	
	public String processPayment(Long orderId) throws InterruptedException{
		
		
		System.out.println("Payment Processing started for order : " + orderId + " at " + LocalDateTime.now());
		Thread.sleep(10000);
		System.out.println("Payment Processing finished for Order " + orderId + " at " + LocalDateTime.now());
		
		return "Payment Sucsess for Order: " + orderId ;
		
	}
	
	public String healthCheck() {
		
		return "Payment Service is UP" ;
	}
	
	
}
