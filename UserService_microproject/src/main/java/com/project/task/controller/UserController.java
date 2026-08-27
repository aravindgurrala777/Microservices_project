package com.project.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.task.dto.UserDto;
import com.project.task.service.UserService;

@RestController
@RequestMapping("/task/user")
public class UserController {

	
	@Autowired
	private UserService userService;
	
	@Value("${user.message:NOT FOUND}")
	private String message;
	
	@Value("${app.environment:NOT FOUND}")
	private String environment;
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long id) throws InterruptedException{
		
		//Thread.sleep(2000); // Simulating a delay of 5 seconds
		
		return userService.getUserById(id).map(ResponseEntity :: ok).orElse(ResponseEntity.notFound().build());
		
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
