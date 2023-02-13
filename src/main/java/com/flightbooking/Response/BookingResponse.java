package com.flightbooking.Response;

import java.util.List;

import com.flightbooking.entity.Flight;
import com.flightbooking.entity.Passenger;

public class BookingResponse {
	private long bookingId;
	private String message;
	private Flight flight;
	private List<Passenger> passenger;
	private double totalPrice;
	
	public long getBookingId() {
		return bookingId;
	}

	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}

	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Flight getFlight() {
		return flight;
	}
	
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	
	public List<Passenger> getPassenger() {
		return passenger;
	}
	
	public void setPassenger(List<Passenger> passenger) {
		this.passenger = passenger;
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
}	
