package com.project.task.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

	
	
	private Long id;
	
	
	private Long userId;
	
	private String product;
	
	
	private double amount;
	
}
