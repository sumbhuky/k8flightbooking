package com.flightbooking.Response;

import java.util.List;

import com.flightbooking.entity.Booking;

public class BookingsForCustomer {
	private List<Booking> bookings;
	private String message;
	public List<Booking> getBookings() {
		return bookings;
	}
	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
