package com.project.task.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.project.task.dto.OrderResponseDto;
import com.project.task.exception.BadRequestException;
import com.project.task.exception.ResourceNotFoundException;

@Service
public class PaymentService {

	
	public RestTemplate restTemplate;
	
	public PaymentService(RestTemplate restTemplate) {
		
		this.restTemplate = restTemplate;
	}	
	
	public String processPayment(Long orderId) throws InterruptedException{
		
		
		System.out.println("Payment Processing started for order : " + orderId + " at " + LocalDateTime.now());
	//	Thread.sleep(10000);
		
		
		if(orderId == null || orderId <= 0) {
			
			throw new BadRequestException("Invalid Order id: " + orderId);
		}
		
		
		try {
			
			String orderServiceUrl = "http://order-service/task/order/" + orderId;
			System.out.println("Validating Order from Order-service: " + orderServiceUrl);
			
			OrderResponseDto orderResponse = restTemplate.getForObject(orderServiceUrl, OrderResponseDto.class);
			
			if(orderResponse == null) {
				
				throw new ResourceNotFoundException("Order not found with id: " + orderId);
			}
			
			System.out.println("Order Validated: " + orderResponse.getProduct());
		}
		catch(HttpClientErrorException.NotFound ex) {
			
			throw new ResourceNotFoundException("Order not found with id: " + orderId);
		}
		catch(HttpClientErrorException.BadRequest ex) {
			
			throw new BadRequestException("Invalid Order id: " + orderId);
		}
		catch(Exception ex) {
			
			System.out.println("Order service call failed: " + ex.getMessage());
			ex.printStackTrace();
			
			throw new RuntimeException("Order Service is Unavailable. Please try again later.");
		}
		
		
		System.out.println("Payment Processing finished for Order " + orderId + " at " + LocalDateTime.now());
		
		return "Payment Sucsess for Order: " + orderId ;
		
	}
	
	public String healthCheck() {
		
		return "Payment Service is UP" ;
	}
	
	
}
