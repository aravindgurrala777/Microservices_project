package com.project.task.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.project.task.dto.OrderResponseDto;
import com.project.task.dto.UserDto;
import com.project.task.entity.Order;
import com.project.task.exception.ResourceNotFoundException;
import com.project.task.exception.UserServiceUnavaliableException;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class OrderService {

	
	private Map<Long, Order> orderMap = new HashMap<>();
	
	
	private RestTemplate restTemplate;
	
//	@Value("${user.service.base-url}")
//	private String userServiceBaseUrl;
	
	public OrderService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	
	
	orderMap.put(101L, new Order(101L, 1L , "Mobile" , 25000) );
	orderMap.put(102L, new Order(102L, 2L , "Charger" , 3000));
	orderMap.put(103L, new Order(103L, 3L , "Laptop" , 62000) );
	orderMap.put(104L, new Order(104L, 4L , "Mouse" , 400));
	
	}
	
	@CircuitBreaker(name = "userService", fallbackMethod = "userFallback")
	@Retry(name = "userService")
	@Bulkhead(name = "userService", type = Bulkhead.Type.SEMAPHORE , fallbackMethod = "userBulkheadFallback")
	public OrderResponseDto getOrderById(Long orderId) {
		
		
		Order order = orderMap.get(orderId);
		 if(order == null) {
			 
			 throw new ResourceNotFoundException("Order not found with id: " + orderId);
		 }
		 
		 
		 UserDto userDto = getUser(order.getUserId());
		 
		 return new OrderResponseDto( order.getId() , order.getProduct() , order.getAmount() , userDto );
		 
	}
		 
		 
		
	
	
	public UserDto validateUser(Long orderId) {
		
	      Order order = orderMap.get(orderId);
	      
	      if(order == null) {
	    	  
	    	  throw new ResourceNotFoundException("order not found with id: "+ orderId);
	      }
	      
	      return getUser(order.getUserId());
	      
		
	}
	
	
	 public UserDto getUser(Long userId) {
		 try {
		 
		//	 String userServiceUrl = "http://localhost:9090/task/user/" + userId;
			 
		// String userServiceUrl = userServiceBaseUrl + "/task/user/" + userId;
		
		String userServiceUrl = "http://user-service/task/user/" + userId ;	 
			 
		System.out.println("Trying to call user service for userId:"+ userId);
		  return restTemplate.getForObject(userServiceUrl, UserDto.class);
		 
	}
	catch(ResourceAccessException e) {
		
		
		System.out.println("TIMEOUT - User Service is slow: " + e.getMessage());
		System.out.println("Network fail, will retry..");
		
		throw e;
	//	throw new UserServiceUnavaliableException("User Service is Unavaliable.Order Cannot be placed... Please try again later", e);
		
	}
		 
 }
	
	
	public OrderResponseDto userFallback(Long orderId, Exception ex) {
		
		System.out.println("User Service is Unavaliable. Fallback method called for orderId: " + orderId);
		System.out.println("Error: " + ex.getMessage());
		
		throw new UserServiceUnavaliableException("User Service is Unavaliable.Order Cannot be placed... Please try again later", ex);
	}
	
public OrderResponseDto userBulkheadFallback(Long orderId, Exception ex) {
		
		System.out.println("User Service is Unavaliable.Bulkhead Fallback method called for orderId: " + orderId);
		System.out.println("Error: " + ex.getMessage());
		
		throw new UserServiceUnavaliableException(" BULKHEAD -  User Service is Unavaliable.Order Cannot be placed... Please try again later", ex);
	}
	
}
