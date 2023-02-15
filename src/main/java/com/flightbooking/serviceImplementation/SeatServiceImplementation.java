package com.flightbooking.serviceImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.flightbooking.entity.Flight;
import com.flightbooking.entity.Seat;
import com.flightbooking.entity.SeatClass;
import com.flightbooking.repository.FlightRepository;
import com.flightbooking.repository.SeatRepository;
import com.flightbooking.service.SeatService;

@Service
public class SeatServiceImplementation implements SeatService {
	SeatRepository seatRepository;
	FlightRepository flightRepository;
	
	@Autowired
	public SeatServiceImplementation(SeatRepository seatRepository, FlightRepository flightRepository) {
		this.seatRepository = seatRepository;
		this.flightRepository = flightRepository;
	}
	@Override
	public List<Seat> getSeatsByFlightId(String flightId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addSeat(Seat seat, String flightId) {
		Flight flight = flightRepository.findById(flightId).get();
		seat.setFlight(flight);
		seatRepository.save(seat);
		return true;
	}

	@Override
	public boolean addSeats(List<Seat> seats, String flightId) {
		seatRepository.saveAll(seats);
		return true;
	}

	@Override
	public boolean deleteSeat(String seatId) {
		seatRepository.deleteById(seatId);
		return true;
	}

	@Override
	public boolean updateSeat(Seat seat) {
		seatRepository.save(seat);
		return true;
	}

	@Override
	public Seat getSeatBySeatId(String seatId) {
		return seatRepository.findById(seatId).get();
	}

	@Override
	public Seat getSeatBySeatNumberAndFlightId(String seatNumber, String flightId) {
		return null;
	}

	@Override
	@Query("select s from Seat s where s.seatNumber = :seatNumber and s.id = :flightId")
	public List<Seat> getSeatsBySeatNumbersAndFlightId(List<String> seatNumber, String flightId) {
		return null;
	}

	@Override
	public List<Flight> getFlightsBySeatClass(SeatClass seatClass) {
		return seatRepository.getFlightsBySeatClass(seatClass);
	}

	@Override
	public List<Flight> getFlightsByAvailability(Boolean available) {
		return seatRepository.getFlightsBySeatAvailability(available);
	}
}
