package com.flightbooking.service;

import java.util.Date;
import java.util.List;

import com.flightbooking.entity.Flight;
import com.flightbooking.entity.Location;

public interface FlightService {
	Flight addFlight(Flight flight) throws Exception;
	boolean deleteFlightById(String id) throws Exception;
	Flight getFlightById(String id) throws Exception;
	boolean updateFlight(Flight flight) throws Exception;
	List<Flight> genericSearch(Location source, 
			Location destination, 
			Date start, 
			Date end,
			Integer duration);
	
	List<Flight> getfindFlightBetweenCities(Location source, Location destination);

	List<Flight> getfindFlightBetweenDateAndTime(Date start, Date end);

}