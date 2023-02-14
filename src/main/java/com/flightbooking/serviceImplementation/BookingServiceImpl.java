package com.flightbooking.serviceImplementation;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightbooking.entity.Booking;
import com.flightbooking.repository.BookingRepository;
import com.flightbooking.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {
	
	@Autowired
	BookingRepository bookingRepository;
	
	@Override
	public Booking createBooking(Booking booking) {
		return bookingRepository.save(booking);
	}

	@Override
	public Booking getBookingById(long bookingId) {
		return bookingRepository.findById(bookingId).get();
	}

	@Override
	public List<Booking> getBookingsByCustomerId(String customerId) {
		return bookingRepository.getBookingsByCustomerId(customerId);
	}

	@Override
	public boolean updateBooking(Booking booking) {
		bookingRepository.save(booking);
		return true;
	}

	@Override
	public List<Booking> getUpcomingBookingsByCustomerId(String customerId, Date currentDate) {
		return bookingRepository.getUpcomingBookingsByCustomerId(customerId, currentDate);
	}

	@Override
	public List<Booking> getAllBookings() {
		 return bookingRepository.findAll();
		
	}
}