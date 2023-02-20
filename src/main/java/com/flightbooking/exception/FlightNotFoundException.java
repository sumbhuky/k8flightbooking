package com.flightbooking.exception;

public class FlightNotFoundException extends Exception {

	//if flight is not available FlightNotFoundException will be executed
	public FlightNotFoundException() {
		super("flight not found");
	}
}
