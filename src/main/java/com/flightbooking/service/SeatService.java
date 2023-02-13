package com.flightbooking.service;

import java.util.List;


import com.flightbooking.entity.Flight;
import com.flightbooking.entity.Seat;
import com.flightbooking.entity.SeatClass;


public interface SeatService {
	
	
	List<Seat> getSeatsByFlightId(String flightId);
	boolean addSeat(Seat seat, String flightId);
	boolean addSeats(List<Seat> seats, String flightId);
	boolean deleteSeat(String seatId);
	boolean updateSeat(Seat seat);
	Seat getSeatBySeatId(String seatId);
	Seat getSeatBySeatNumberAndFlightId(String seatNumber, String flightId);
	List<Seat> getSeatsBySeatNumbersAndFlightId(List<String> seatNumber, String flightId);
	List<Flight> getFlightsBySeatClass(SeatClass seatClass);
	List<Flight> getFlightsByAvailability(Boolean available);
	

}
