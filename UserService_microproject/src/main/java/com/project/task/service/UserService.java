package com.project.task.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.task.dto.UserDto;
import com.project.task.entity.User;
import com.project.task.exception.ResourceNotFoundException;

@Service
public class UserService {

	
	private Map<Long, User> userMap = new HashMap<>();
	
	public UserService() {
		
		userMap.put(1L, new User(1L,"Aravind","aravind@gmail.com", "Male"));
		userMap.put(2L, new User(2L , "Satish", "satish@gmail.com", "Male" ));
		userMap.put(3L, new User(3L,"Ramya" , "ramya@gmail.com", "Female")) ;
		userMap.put(4L,  new User(4L , "Rajesh" , "rajesh@gmail.com" , "Male"));
		
		
	}
	
	public Optional<UserDto> getUserById(Long id){
		
		User user = userMap.get(id);
		
		if(user == null) {
			
			throw new ResourceNotFoundException("User not found with id: " + id);
		}
		
		return Optional.of(formatDto(user));
		
	}
	
	private UserDto formatDto(User user) {
		
		UserDto dto = new UserDto();
		
		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());
		dto.setGender(user.getGender());
		
		return dto;
		
	}
	
	
}
