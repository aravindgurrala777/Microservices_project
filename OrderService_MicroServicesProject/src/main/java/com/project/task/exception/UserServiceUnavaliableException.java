package com.project.task.exception;

public class UserServiceUnavaliableException extends RuntimeException {

	
	public UserServiceUnavaliableException(String message) {
		super(message);
	}
	
	public UserServiceUnavaliableException(String message, Throwable cause) {
		super(message,cause);
	}
	
	
}
