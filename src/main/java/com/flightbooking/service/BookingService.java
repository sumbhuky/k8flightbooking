package com.flightbooking.service;

import java.util.Date;
import java.util.List;

import com.flightbooking.entity.Booking;

public interface BookingService {
	Booking createBooking(Booking booking);
	Booking getBookingById(long bookingId);
	List<Booking> getBookingsByCustomerId(String customerId);
	List<Booking> getUpcomingBookingsByCustomerId(String customerId, Date currentDate);
	boolean updateBooking(Booking booking);
}
