package com.project.task.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data

public class ErrorResponseDto {

	
	private LocalDateTime timestamp;
	
	
	private int value;
	
	private String error;
	
	private String message;
	
	private String path;

	public ErrorResponseDto( int value, String error, String message, String path) {
		super();
		this.timestamp = LocalDateTime.now();
		this.value = value;
		this.error = error;
		this.message = message;
		this.path = path;
	}
	
	
	
}
