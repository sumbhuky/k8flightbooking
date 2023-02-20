package com.flightbooking.exception;

public class FlightAlreadyExistsException extends Exception {

	//If the flight already existed it will throw FlightAlreadyExistsException
	public FlightAlreadyExistsException() {
		super("Flight already exists");
	}
}
