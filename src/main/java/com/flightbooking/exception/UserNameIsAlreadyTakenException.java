package com.flightbooking.exception;

public class UserNameIsAlreadyTakenException extends Exception {
	
	//If username is already taken UserNameIsAlreadyTakenException will be executed
	public UserNameIsAlreadyTakenException() {
		super("Customer already exists.");
	}
}
