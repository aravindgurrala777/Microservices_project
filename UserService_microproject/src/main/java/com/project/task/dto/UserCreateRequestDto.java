package com.project.task.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequestDto {

	@NotBlank(message = "Name is required")
	String name;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
	String email;
	
	@NotBlank(message = "Gender is required")
	@Pattern(regexp = "male|female", message = "Gender must be male or female")
	String gender;
	
	
}
