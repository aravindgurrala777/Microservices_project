package com.project.task.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import com.project.task.dto.UserCreateRequestDto;
import com.project.task.dto.UserDto;
import com.project.task.entity.User;
import com.project.task.exception.BadRequestException;
import com.project.task.exception.ResourceNotFoundException;
import com.project.task.repository.UserRepository;

@Service
public class UserService {

	
//	private Map<Long, User> userMap = new HashMap<>();
//	
//	public UserService() {
//		
//		userMap.put(1L, new User(1L,"Aravind","aravind@gmail.com", "Male"));
//		userMap.put(2L, new User(2L , "Satish", "satish@gmail.com", "Male" ));
//		userMap.put(3L, new User(3L,"Ramya" , "ramya@gmail.com", "Female")) ;
//		userMap.put(4L,  new User(4L , "Rajesh" , "rajesh@gmail.com" , "Male"));
//		
//		
//	}
	
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public UserDto createUser(UserCreateRequestDto requestDto) {
		
		String cid = MDC.get("correlationId");
		
		
		if(userRepository.existsByEmail(requestDto.getEmail())) {
			
			throw new BadRequestException("User with email " + requestDto.getEmail() + " already exists");
		}
		
		User user = new User();
		
		user.setName(requestDto.getName());
		user.setEmail(requestDto.getEmail());
		user.setGender(requestDto.getGender());
		
		User saveduser = userRepository.save(user);
		
		System.out.println(cid + " -- User created with id: " + saveduser.getId() + " and email: " + saveduser.getEmail());
		
		return new UserDto(saveduser.getId(), saveduser.getName(), saveduser.getEmail(), saveduser.getGender());
		
	}
	
	
	
	public Optional<UserDto> getUserById(Long id){
		
		String cid = MDC.get("correlationId");
		System.out.println(cid + " -- UserService....getUserById called with id: " + id);
		
		
		if(id == null || id <= 0) {
			
			throw new BadRequestException("Invalid User id: " + id);
		}
		
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
		
//		if(user == null ) {
//			
//			throw new ResourceNotFoundException("User not found with id: " + id);
//		}
		
		return Optional.of(formatDto(user));
		
	}
	
	
	
	public void deleteUserById(Long id) {
	
		String cid = MDC.get("correlationId");
		System.out.println(cid + " -- UserService....deleteUserById called with id: " + id);
		
		if(!userRepository.existsById(id))
		{
			throw new ResourceNotFoundException("User not found with id: " + id);
		}
		
		userRepository.deleteById(id);
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
