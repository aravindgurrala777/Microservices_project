package com.project.task.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.task.dto.ErrorResponseDto;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class )
	public ResponseEntity<ErrorResponseDto> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request){
		
		
		ErrorResponseDto error = new ErrorResponseDto(    
				 
				   HttpStatus.NOT_FOUND.value(),
				   "Not Found",
				   ex.getMessage(),
				   request.getRequestURI()
				);
		
		return new ResponseEntity(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponseDto> handleBadRequest(BadRequestException ex, HttpServletRequest request){
		
		
		ErrorResponseDto error = new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), "Bad Request", ex.getMessage(), request.getRequestURI());
	
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception ex, HttpServletRequest request){
		
		ErrorResponseDto error = new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error" , ex.getMessage() , request.getRequestURI());
		
		return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);                    //httpstaus.service_unavaliable
		
	}
	
	@ExceptionHandler(ServiceUnavaliableException.class)
	public ResponseEntity<ErrorResponseDto> handleServiceUnavaliable(ServiceUnavaliableException ex, HttpServletRequest request){
		
		
		ErrorResponseDto error = new ErrorResponseDto(HttpStatus.SERVICE_UNAVAILABLE.value(), "Service Unavaliable", ex.getMessage(), request.getRequestURI());
		
		return new ResponseEntity(error, HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler(UserServiceUnavaliableException.class)
	public ResponseEntity<Map<String, Object>> handleUserServiceUnavaliable(UserServiceUnavaliableException ex){
		
		Map<String, Object> response = new HashMap<>();
		
		response.put("status", 503);
		response.put("error", "User_Service_Unavaliable");
		response.put("message", ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
		
	}
	
	
}
