package com.project.task.controller;

import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.task.entity.User;
import com.project.task.repository.UserRepository;
import com.project.task.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/task/auth")
@RequiredArgsConstructor
public class AuthController {

	
	private final UserRepository repo;
	private final JwtUtil jwtUtil;
	private final PasswordEncoder encoder;
	
	@PostMapping("/register")
	public String register(@RequestBody User user) {
		
		user.setPassword(encoder.encode(user.getPassword()));
		repo.save(user);
		
		return "User registered successfully";
		
	}
	
	@PostMapping("/login")
	public Map<String, String> login(@RequestBody Map<String,String> body) {
		
		User u = repo.findByUsername(body.get("username")).orElseThrow(() -> new RuntimeException("User not found"));
		
		if(encoder.matches(body.get("password"), u.getPassword())) {
			
			String token = jwtUtil.generateToken(u.getUsername());
			
			return Map.of("token", token);
		}
		
		throw new RuntimeException("Invalid Password");
		
	}
	
	
}
