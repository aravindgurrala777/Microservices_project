package com.project.task.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.task.service.PaymentService;

@RestController
@RequestMapping("/task/payment")
public class PaymentController {

	
	private final PaymentService paymentService;
	
	public PaymentController(PaymentService paymentService) {
		
		this.paymentService = paymentService;
	}
	
	@GetMapping("/health")
	public String health() {
		
		return paymentService.healthCheck();
	}
	
	@GetMapping("/process/{orderId}")
	public String processPayment(@PathVariable Long orderId) throws InterruptedException {
		
		
		return paymentService.processPayment(orderId);
	}
	
	
}
