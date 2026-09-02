package com.project.task.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class CreateOrderRequestDto {

	@NotNull(message = "User ID cannot be null")
	private Long userId;
	
	@NotBlank(message = "Product name cannot be blank")
	@Size(min = 2 ,max = 50, message = "Product name cannot exceed 50 characters")
	private String product;
	
	@NotNull(message = "Amount cannot be null")
	@Positive(message = "Amount must be a positive value")
	@Min(value = 1, message = "Minimum amount is 1")
	private Double amount;

	public CreateOrderRequestDto(@NotNull(message = "User ID cannot be null") Long userId,
			@NotBlank(message = "Product name cannot be blank") @Size(min = 2, max = 50, message = "Product name cannot exceed 50 characters") String product,
			@NotNull(message = "Amount cannot be null") @Positive(message = "Amount must be a positive value") @Min(value = 1, message = "Minimum amount is 1") Double amount) {
		super();
		this.userId = userId;
		this.product = product;
		this.amount = amount;
	}

	
	
	public CreateOrderRequestDto() {
		super();
	}



	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
	
	
}
