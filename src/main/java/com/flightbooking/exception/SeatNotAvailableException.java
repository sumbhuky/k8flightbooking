package com.flightbooking.exception;

public class SeatNotAvailableException extends Exception {

	private static final long serialVersionUID = 8466876469894167109L;

	//If seat is not available SeatNotAvailableException will be executed
	public SeatNotAvailableException(String message) {
		super(message);
	}
}
