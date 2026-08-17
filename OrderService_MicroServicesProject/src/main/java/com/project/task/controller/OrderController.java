package com.project.task.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.task.dto.OrderResponseDto;
import com.project.task.service.OrderService;
import com.project.task.service.PaymentClient;

@RestController
@RequestMapping("/task/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	

	private final PaymentClient paymentClient;
	
	
	public OrderController(PaymentClient paymentClient) {
		this.paymentClient = paymentClient;
		
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id){
		
		OrderResponseDto response = orderService.getOrderById(id);
		
		if(response == null) {
			
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/place/{orderId}")
	public CompletableFuture<String> placeOrder(@PathVariable long orderId) {
		
		System.out.println("Order received " + orderId);
		
		
		return paymentClient.processPayment(orderId)
			.thenApply(result -> "Order ID: " + orderId + " | " + result);
            	
	}
	
}
