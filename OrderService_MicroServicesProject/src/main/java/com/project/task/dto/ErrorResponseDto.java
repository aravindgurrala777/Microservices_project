package com.project.task.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {

	private LocalDateTime timestamp;
	private int status;
	private String error;
	private String message;
	private String path;
	public ErrorResponseDto(int status, String error, String message, String path) {
		super();
		this.timestamp = LocalDateTime.now();
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}
	
	
	
	
}
