package com.flightbooking.request;

import java.util.List;

import com.flightbooking.entity.Passenger;

public class BookingRequest {
	private String customerId;
	private String flightId;
	private List<String> seatIds;
	private List<Passenger> passengers;

	public BookingRequest() {}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getFlightId() {
		return flightId;
	}

	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}

	public List<String> getSeatIds() {
		return seatIds;
	}

	public void setSeatIds(List<String> seatIds) {
		this.seatIds = seatIds;
	}
	
	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}
}
