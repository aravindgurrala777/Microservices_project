package com.project.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

	
	private Long id;
	
	
	private String productName;
	
	
	private double amount;
	
	private UserDto user;
	
	
}
