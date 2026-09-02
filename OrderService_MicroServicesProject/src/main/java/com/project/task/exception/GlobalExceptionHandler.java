package com.project.task.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.task.dto.ErrorResponseDto;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class )
	public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request){
		
		
		ApiError error = ApiError.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.NOT_FOUND.value())
				.error("Resource_Not_Found")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build();
		
		return new ResponseEntity(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex, HttpServletRequest request){
		
		
	//	ErrorResponseDto error = new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), "Bad Request", ex.getMessage(), request.getRequestURI());
	
		
		
		ApiError error = ApiError.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.error("Bad_Request")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.traceId(UUID.randomUUID().toString())
				.build();
		
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception ex, HttpServletRequest request){
		

		
		ApiError error = ApiError.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.error("Internal_Server_Error")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.traceId(UUID.randomUUID().toString())
				.build();
		
		return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);                    
		
	}
	
//	@ExceptionHandler(ServiceUnavaliableException.class)
//	public ResponseEntity<ErrorResponseDto> handleServiceUnavaliable(ServiceUnavaliableException ex, HttpServletRequest request){
//		
//		
//       ApiError error = ApiError.builder()
//				.timestamp(LocalDateTime.now())
//				.status(HttpStatus.SERVICE_UNAVAILABLE.value())
//				.error("Service_Unavaliable")
//				.message(ex.getMessage())
//				.path(request.getRequestURI())
//				.traceId(UUID.randomUUID().toString())
//				.build();
//		
//		
//		return new ResponseEntity(error, HttpStatus.SERVICE_UNAVAILABLE);
//	}
	
	@ExceptionHandler(UserServiceUnavaliableException.class)
	public ResponseEntity<Map<String, Object>> handleUserServiceUnavaliable(UserServiceUnavaliableException ex, HttpServletRequest request){
		ApiError error = ApiError.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.SERVICE_UNAVAILABLE.value())
				.error("User_Service_Unavaliable")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.traceId(UUID.randomUUID().toString())
				.build();
		
		return new ResponseEntity(error , HttpStatus.SERVICE_UNAVAILABLE);
		
	}
	
	
}
