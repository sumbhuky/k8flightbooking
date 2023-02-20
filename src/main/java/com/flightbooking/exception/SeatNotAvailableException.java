package com.flightbooking.exception;

public class SeatNotAvailableException extends Exception {

	//If seat is not available SeatNotAvailableException will be executed
	public SeatNotAvailableException(String message) {
		super(message);
	}
}
