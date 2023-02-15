package com.flightbooking.exception;

public class UserNotFoundException extends Exception {
	private static final long serialVersionUID = 1554323716144750294L;

	//If user will not found UserNotFoundException will be executed
	public UserNotFoundException() {
		super("User doesn't exist.");
	}
}
