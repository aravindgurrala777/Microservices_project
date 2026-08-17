package com.project.task.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.task.dto.OrderResponseDto;
import com.project.task.dto.UserDto;
import com.project.task.entity.Order;
import com.project.task.exception.ResourceNotFoundException;

@Service
public class OrderService {

	
	private Map<Long, Order> orderMap = new HashMap<>();
	private RestTemplate restTemplate;
	
	@Value("${user.service.base-url}")
	private String userServiceBaseUrl;
	
	public OrderService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	
	
	orderMap.put(101L, new Order(101L, 1L , "Mobile" , 25000) );
	orderMap.put(102L, new Order(102L, 2L , "Charger" , 3000));
	orderMap.put(103L, new Order(103L, 3L , "Laptop" , 62000) );
	orderMap.put(104L, new Order(104L, 4L , "Mouse" , 400));
	
	}
	
	
	public OrderResponseDto getOrderById(Long orderId) {
		
		
		Order order = orderMap.get(orderId);
		 if(order == null) {
			 
			 throw new ResourceNotFoundException("Order not found with id: " + orderId);
		 }
		 
		 
		// String userServiceUrl = "http://localhost:9090/task/user/" + order.getUserId();
		 
		 String userServiceUrl = userServiceBaseUrl + "/task/user/" + order.getUserId();
		 UserDto userDto = restTemplate.getForObject(userServiceUrl, UserDto.class);
		 
		 
		 return new OrderResponseDto(  order.getId() , order.getProduct() , order.getAmount() , userDto);
		 
	}
	
	
}
