package com.project.task.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.project.task.dto.CreateOrderRequestDto;
import com.project.task.dto.OrderResponseDto;
import com.project.task.dto.UserDto;
import com.project.task.entity.Order;
import com.project.task.exception.BadRequestException;
import com.project.task.exception.ResourceNotFoundException;
import com.project.task.exception.UserServiceUnavaliableException;
import com.project.task.repository.OrderRepository;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;

@Service
public class OrderService {

	
//	private Map<Long, Order> orderMap = new HashMap<>();
	
	private final UserServiceClient userServiceClient;
	
	private final OrderRepository orderRepository;
	
	
	public OrderService(UserServiceClient userServiceClient, OrderRepository orderRepository) {
		
		this.userServiceClient = userServiceClient;
		this.orderRepository = orderRepository;
	}
	
	@Transactional
	public OrderResponseDto createOrder(CreateOrderRequestDto request){
		
		String cid = MDC.get("correlationId");
		if(cid == null) cid = MDC.get("X-Correlation-ID");
		if(cid == null) cid = "No-CID";
		
		System.out.println("Correlation ID in OrderService.createOrder: " + cid + " for userId: " + request.getUserId());
		System.out.println(cid + " -- Calling User service to validate user with id: " + request.getUserId());
		
		
		UserDto userDto = userServiceClient.getUser(request.getUserId());
		
		System.out.println(cid + " -- User service response for userId: " + request.getUserId() + " is: " + userDto);
		
		if(userDto == null) {
			
			System.out.println(cid + " -- User not found with id: " + request.getUserId());
			
			throw new ResourceNotFoundException("User not found with id: " + request.getUserId());
		}
		
		Order order = new Order();
		order.setUserId(request.getUserId());
		order.setProduct(request.getProduct());
		order.setAmount(request.getAmount());
		
		Order savedOrder = orderRepository.save(order);
		
		System.out.println(cid + " -- Order created successfully with id: " + savedOrder.getId() + " for userId: " + request.getUserId());
		
		return new OrderResponseDto(savedOrder.getId(), savedOrder.getProduct(), savedOrder.getAmount(), userDto);
	}
	
	
	public OrderResponseDto getOrderById(Long orderId) {
		
		String cid = MDC.get("correlationId");
		if(cid == null) cid = MDC.get("X-Correlation-ID");
		if(cid == null) cid = "No-CID";
		
		
		System.out.println("Correlation ID in OrderService.getOrderById: " + cid + " for orderId: " + orderId);
		
		if(orderId == null || orderId <= 0) {
			
			throw new BadRequestException("Invalid Order id" + orderId);
		}
		
		
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
		
		
		System.out.println(cid + " -- Order found with id: " + orderId + " for userId: " + order.getUserId());
		 
		 UserDto userDto = userServiceClient.getUser(order.getUserId());
		 
		 System.out.println(cid + " -- User service response for userId: " + order.getUserId() + " is: " + userDto);
		 return new OrderResponseDto( order.getId() , order.getProduct() , order.getAmount() , userDto );
		 
	}
		 
	public UserDto validateUser(Long orderId) {
		
		String cid = MDC.get("correlationId");
		if(cid == null) cid = MDC.get("X-Correlation-ID");
		
		System.out.println("Correlation ID in OrderService.validateUser: " + cid + " for orderId: " + orderId);
		
		
	      Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
	      
//	      if(order == null) {
//	    	  
//	    	  throw new ResourceNotFoundException("order not found with id: "+ orderId);
//	      }
	      
	      System.out.println(cid + " -- Order found with id: " + orderId + " for userId: " + order.getUserId());
	      
	      return userServiceClient.getUser(order.getUserId());
	      
		
	}
	
	@Transactional
	public String deleteOrderById(Long orderId) {
		
		String cid = MDC.get("correlationId");
		if(cid == null) cid = MDC.get("X-Correlation-ID");
		
		System.out.println("Correlation ID in OrderService.deleteOrderById: " + cid + " for orderId: " + orderId);
		
		
		if(orderId == null || orderId <= 0) {
			
			throw new BadRequestException("Invalid Order id" + orderId);
		}
		
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
		
		orderRepository.delete(order);
		
		System.out.println(cid + " -- Order deleted successfully with id: " + orderId);
		
		return "Order deleted successfully with id: " + orderId;
	}
	
	
}
