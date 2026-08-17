package com.project.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
		
		return userService.getUserById(id).map(ResponseEntity :: ok).orElse(ResponseEntity.notFound().build());
		
	}
	
	
}
