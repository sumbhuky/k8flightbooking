package com.flightbooking.exception;

public class UserNameIsAlreadyTakenException extends Exception {
	private static final long serialVersionUID = -1473268420622597923L;

	//If username is already taken UserNameIsAlreadyTakenException will be executed
	public UserNameIsAlreadyTakenException() {
		super("Customer already exists.");
	}
}
