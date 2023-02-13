package com.flightbooking.exception;

public class FlightAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public FlightAlreadyExistsException() {
		super("Flight already exists");
	}
}
