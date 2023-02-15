package com.flightbooking.exception;

public class FlightAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	//If the flight already existed it will throw FlightAlreadyExistsException
	public FlightAlreadyExistsException() {
		super("Flight already exists");
	}
}
