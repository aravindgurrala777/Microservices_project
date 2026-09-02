package com.project.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

	
	private Long id;
	private Long userId;
	private String product;
	private Integer amount;
	
}
