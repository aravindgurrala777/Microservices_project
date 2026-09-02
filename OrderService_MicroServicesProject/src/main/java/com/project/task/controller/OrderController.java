package com.project.task.controller;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.task.dto.CreateOrderRequestDto;
import com.project.task.dto.OrderResponseDto;
import com.project.task.service.OrderService;
import com.project.task.service.PaymentClient;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/task/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Value("${order.message:NOT FOUNDED}")
	private String message;
	
	@Value("${app.environment:NOT FOUND}")
	private String environment;

	private final PaymentClient paymentClient;
	
	
	public OrderController(PaymentClient paymentClient) {
		this.paymentClient = paymentClient;
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody CreateOrderRequestDto  request){
		
		
		OrderResponseDto response = orderService.createOrder(request);
		
		return ResponseEntity.status( HttpStatus.CREATED).body(response);
	}
	
	@PreAuthorize("hasRole('USER')")  
	@GetMapping("/{id}")
	public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id){
		
		OrderResponseDto response = orderService.getOrderById(id);
		
		if(response == null) {
			
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(response);
	}
	
	@PreAuthorize("hasRole('USER')")  
	@GetMapping("/place/{orderId}")
	public CompletableFuture<String> placeOrder(@PathVariable long orderId) {
		
		
		System.out.println("Order received " + orderId);
		
		orderService.validateUser(orderId);
		
		return paymentClient.processPayment(orderId)
			.thenApply(result -> "Order ID: " + orderId + " | " + result);
            	
	}
	
	@PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deleteOrder(@PathVariable Long id){
		
		orderService.deleteOrderById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping("/message")
	public String message() {
		return message;
	}
	
	@GetMapping("/environment")
	public String environment() {
		return environment;
	}
	
	
}
