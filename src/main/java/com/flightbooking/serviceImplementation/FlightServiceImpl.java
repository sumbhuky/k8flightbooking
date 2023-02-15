package com.flightbooking.serviceImplementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightbooking.entity.Flight;
import com.flightbooking.entity.Location;
import com.flightbooking.entity.Seat;
import com.flightbooking.exception.FlightNotFoundException;
import com.flightbooking.repository.FlightRepository;
import com.flightbooking.service.FlightService;

@Service	
public class FlightServiceImpl implements FlightService {
	FlightRepository flightRepository;
	
	@Autowired
	public FlightServiceImpl(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}

	@Override
	public Flight addFlight(Flight flight) throws Exception {
		
		return flightRepository.save(flight);
	}

	@Override
	public boolean deleteFlightById(String id) throws Exception {
		flightRepository.deleteById(id);
		return true;
	}

	@Override
	public Flight getFlightById(String id) throws Exception {
		Optional<Flight> flight = flightRepository.findById(id);
		if(flight.isEmpty()) {
			throw new FlightNotFoundException();
		}
		return flight.get();		
	}

	@Override
	public boolean updateFlight(Flight flight) throws Exception {
		flightRepository.save(flight);
		return true;
	}

	@Override
	public List<Flight> genericSearch(Location source, Location destination, Date start, Date end, Integer duration) {
		List<Flight> flights = flightRepository.genericSearch(source, destination, start, end, duration);
		removeUnavailableSeats(flights);
		return flights;
	}
	
	
	private void removeUnavailableSeats(List<Flight> flights) {
		for(Flight flight : flights) {
			List<Seat> availableSeats = new ArrayList<Seat>();
			for(Seat seat : flight.getSeats()) {
				if(seat.getAvailable()) {
					availableSeats.add(seat);
				}
			}
			
			flight.setSeats(availableSeats);
		}
	}

	@Override
	public List<Flight> getfindFlightBetweenCities(Location source, Location destination) {
		return flightRepository.findFlightBetweenCities(source, destination);
		
	}

	@Override
	public List<Flight> getfindFlightBetweenDateAndTime(Date start, Date end) {
		return flightRepository.findFlightBetweenDateAndTime(start, end);
	}
}