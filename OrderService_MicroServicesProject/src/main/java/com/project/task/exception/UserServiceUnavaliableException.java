package com.project.task.exception;

public class UserServiceUnavaliableException extends RuntimeException {

	public UserServiceUnavaliableException(String message) {
		
		super(message);
	}

	public UserServiceUnavaliableException(String string, Throwable ex) {

	super(string, ex);
	}

}
