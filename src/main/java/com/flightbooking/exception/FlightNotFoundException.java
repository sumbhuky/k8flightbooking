package com.flightbooking.exception;

public class FlightNotFoundException extends Exception {

	private static final long serialVersionUID = 1409145166285825028L;

	//if flight is not available FlightNotFoundException will be executed
	public FlightNotFoundException() {
		super("flight not found");
	}
}
