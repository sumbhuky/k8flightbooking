package com.flightbooking.exception;

public class WrongPasswordException extends Exception {
	private static final long serialVersionUID = 2067417747542211687L;
	
	//If user entered wrong password WrongPasswordException will be executed
	public WrongPasswordException() {
		super("Wrong password.");
	}
}
