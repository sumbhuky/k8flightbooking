package com.flightbooking.exception;

public class UserNotFoundException extends Exception {

	//If user will not found UserNotFoundException will be executed
	public UserNotFoundException() {
		super("User doesn't exist.");
	}
}
