package com.project.task.controller;

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

import com.project.task.dto.UserCreateRequestDto;
import com.project.task.dto.UserDto;
import com.project.task.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/task/user")
public class UserController {

	
	@Autowired
	private UserService userService;
	
	@Value("${user.message:NOT FOUND}")
	private String message;
	
	@Value("${app.environment:NOT FOUND}")
	private String environment;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserCreateRequestDto requestDto) {
		
		UserDto userDto = userService.createUser(requestDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
	}
	
	
	@PreAuthorize("hasRole('USER')")                    //("hasAnyRole('MANAGER','ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long id) throws InterruptedException{
		
		//Thread.sleep(2000); // Simulating a delay of 5 seconds
		
		return userService.getUserById(id).map(ResponseEntity :: ok).orElse(ResponseEntity.notFound().build());
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
		
		
		
		userService.deleteUserById(id);
		
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
