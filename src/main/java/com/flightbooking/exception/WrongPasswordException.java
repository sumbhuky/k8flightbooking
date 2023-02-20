package com.flightbooking.exception;

public class WrongPasswordException extends Exception {
	
	//If user entered wrong password WrongPasswordException will be executed
	public WrongPasswordException() {
		super("Wrong password.");
	}
}
