package com.project.task.service;

import org.slf4j.MDC;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.project.task.dto.UserDto;
import com.project.task.exception.UserServiceUnavaliableException;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class UserServiceClient {

    private final RestTemplate restTemplate;
    
    public UserServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
   
    @CircuitBreaker(name = "userService", fallbackMethod = "userFallback")
    @Retry(name = "userService")
    @Bulkhead(name = "userService")    
    public UserDto getUser(Long userId) {
        
    	
    	String cid = MDC.get("correlationId");
    	if(cid == null) cid = MDC.get("X-Correlation-ID");
    	
    	
    	if(cid == null ) {
    		
    		try {
    			ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
				if(attrs != null) {
					cid = attrs.getRequest().getHeader("X-Correlation-ID");
				}
			} catch (Exception e) {
				System.out.println("Error retrieving correlation ID from request attributes: " + e.getMessage());
    		}
    			
    	}
    	
    	
    	if(cid == null) cid = "No-CID";
    	
    	
        System.out.println(cid + " -- Calling User service to validate user with id: " + userId);
        String userServiceUrl = "http://user-service/task/user/" + userId;
        System.out.println(cid + " -- Order service calling User service URL: " + userServiceUrl);
        
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String authHeader = attrs.getRequest().getHeader("Authorization");
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        headers.set("X-Correlation-ID", cid);
        
        
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        
        System.out.println(cid + " -- user service, response received for userId: " + userId);
        
        
        
        return restTemplate.exchange(userServiceUrl, HttpMethod.GET, entity, UserDto.class).getBody();
    }
    
    public UserDto userFallback(Long userId, Throwable ex) {
    	
    	
    	String cid = MDC.get("correlationId");
    	if(cid == null) cid = MDC.get("X-Correlation-ID");
    	if(cid == null) cid = "No-CID";
    	
    	
    	
        System.out.println(cid + " -- User Service is Unavailable. Fallback method called for userId: " + userId);
        System.out.println(cid + " -- Error: " + ex.getMessage());
        throw new UserServiceUnavaliableException("User Service is Unavailable. Please try again later.", ex);
    }
    
    public UserDto userBulkheadFallback(Long userId, Throwable ex) {
    	
    	String cid = MDC.get("correlationId");
    	if(cid == null) cid = MDC.get("X-Correlation-ID");
    	if(cid == null) cid = "No-CID";
    	
    	
        System.out.println(cid + " -- User Service is Unavailable. Bulkhead Fallback method called for userId: " + userId);
        System.out.println(cid + " -- Error: " + ex.getMessage());
        throw new UserServiceUnavaliableException("BULKHEAD - User Service is Unavailable. Please try again later.", ex);
    }    
}