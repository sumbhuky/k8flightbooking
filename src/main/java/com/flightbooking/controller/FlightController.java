package com.flightbooking.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.flightbooking.Response.Response;
import com.flightbooking.Response.SearchResult;
import com.flightbooking.entity.Flight;
import com.flightbooking.entity.Seat;
import com.flightbooking.entity.SeatClass;
import com.flightbooking.exception.FlightAlreadyExistsException;
import com.flightbooking.exception.FlightNotFoundException;
import com.flightbooking.request.FlightSearchRequest;
import com.flightbooking.request.FlightSearchRequestByCities;
import com.flightbooking.request.FlightSearchRequestByDateAndTime;

import com.flightbooking.service.FlightService;
import com.flightbooking.service.SeatService;

@RestController
public class FlightController {
	
	//Injecting FlightService class
	@Autowired
	FlightService flightService;
	
	//Injecting SeatService class
	@Autowired
	SeatService seatService;
	
	private static ResponseEntity<Response> flightAddedResponse 
	= new ResponseEntity<Response>(new Response(null, "Flight added."), HttpStatus.CREATED);
	
	private static ResponseEntity<Response> flightDateResponse 
	= new ResponseEntity<Response>(new Response(null, "Pleas choose future date to add flights"), HttpStatus.BAD_REQUEST);


	private static ResponseEntity<Response> flightDeletedResponse 
	= new ResponseEntity<Response>(new Response(null, "Flight deleted."), HttpStatus.CREATED);

	private static ResponseEntity<Response> flightAlreadyExistsExceptionResponse 
		= new ResponseEntity<Response>(new Response(null, "Flight id is already taken."), HttpStatus.BAD_REQUEST);

	private static ResponseEntity<Response> internalServerErrorResponse 
		= new ResponseEntity<Response>(new Response(null, "Something went wrong."), HttpStatus.INTERNAL_SERVER_ERROR);
	
	private static ResponseEntity<SearchResult> internalServerErrorResponseForSearch 
	= new ResponseEntity<SearchResult>(new SearchResult(null, null, null, "Something went wrong."), HttpStatus.INTERNAL_SERVER_ERROR);

	private static ResponseEntity<SearchResult> flightNotFoundExceptionResponse 
		= new ResponseEntity<SearchResult>(new SearchResult(null, null, null, "Flight not found."), HttpStatus.BAD_REQUEST);
	
	//Adding flight details in the database
	@PostMapping("v1/api/flight")
	public ResponseEntity<Response> addFlight(@Valid @RequestBody Flight flight, BindingResult result) {
		
		if(result.hasErrors()) {
			Response response = new Response(result.getFieldErrors(), "Errors found during validation");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			Flight existingFlight = null;
		
		
		try { 
			//if the flight id is in the database, it will store in existingFlight
			//so the existing flight is Not null
			 existingFlight = flightService.getFlightById(flight.getId());
		}catch(Exception e) {
			//do nothing as flight not found exception thrown here
		}
		
		if(existingFlight != null) {
			throw new FlightAlreadyExistsException();
		}
//		LocalDate currentDate = LocalDate.now();
//		LocalDate flightDate = flight.getArrival();
//		if (flightDate.isBefore(currentDate)) {
//	      return flightDateResponse;
//	    }
		
		LocalDate currentDate2 = LocalDate.now();
		LocalDate flightDate2 = flight.getDeparture();
		if (flightDate2.isBefore(currentDate2)) {
	      return flightDateResponse;
	    }
		
		flightService.addFlight(flight);
		return flightAddedResponse;
		}catch(FlightAlreadyExistsException e) {
			return flightAlreadyExistsExceptionResponse;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return internalServerErrorResponse;
		}
	}
	
	//Retrieving flight details using flightId
	@GetMapping("v1/api/flight/")
	public ResponseEntity<SearchResult> addFlight(@NotEmpty @PathVariable String flightId) {
		try { 
			Flight flight = flightService.getFlightById(flightId);
			return new ResponseEntity<SearchResult>(new SearchResult(null, Arrays.asList(flight), null, 
					"Successfully got the flight."), HttpStatus.OK);
		}
		catch(FlightNotFoundException e) {
			return flightNotFoundExceptionResponse;
		}
		catch(Exception e) {
			return internalServerErrorResponseForSearch;
		}
	}
	
	//deleting flight details using flightId
	@DeleteMapping("v1/api/flight/{flightId}")
	
	public ResponseEntity<Response> deleteFlight(@NotEmpty @PathVariable String flightId) {
		try { 
			flightService.deleteFlightById(flightId);
			return flightDeletedResponse;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return internalServerErrorResponse;
		}
	}
	
	//Updating flight details using flightId
	@PutMapping("v1/api/flight/{flightId}")
	public ResponseEntity<Response> updateFlight(@NotEmpty @PathVariable String flightId, @RequestBody Flight newFlight) {
		try {
			Flight currentFlight = flightService.getFlightById(flightId);
			
			if(newFlight.getAirline() != null) {
				currentFlight.setAirline(newFlight.getAirline());
			}
			
			if(newFlight.getSource() != null) {
				currentFlight.setSource(newFlight.getSource());
			}
			
			if(newFlight.getDestination() != null) {
				currentFlight.setDestination(newFlight.getDestination());
			}
			
			if(newFlight.getDeparture() != null) {
				currentFlight.setDeparture(newFlight.getDeparture());
			}
			
			if(newFlight.getArrival() != null) {
				currentFlight.setArrival(newFlight.getArrival());
			}
			
			if(newFlight.getPremiumSeatPrice() != null) {
				currentFlight.setPremiumSeatPrice(newFlight.getPremiumSeatPrice());
			}
			
			if(newFlight.getEconomySeatPrice() != null) {
				currentFlight.setEconomySeatPrice(newFlight.getEconomySeatPrice());
			}
			
			if(newFlight.getBusinessSeatPrice() != null) {
				currentFlight.setBusinessSeatPrice(newFlight.getBusinessSeatPrice());
			}
			
			flightService.updateFlight(currentFlight);
			
			return new ResponseEntity<Response>(new Response(null, "Successfully updated flight"), HttpStatus.OK);
		}
		catch(FlightNotFoundException e) {
			return new ResponseEntity<Response>(new Response(null, e.getMessage()), HttpStatus.NOT_FOUND);
		}
		catch(Exception e) {
			return internalServerErrorResponse;
		}
	}
	
	//This getFlights method used in search method below
	public List<Flight> getFlights(FlightSearchRequest request) {
		List<Flight> flights = flightService.genericSearch(request.getSource(), 
				request.getDestination(), 
				request.getStart(), 
				request.getEnd(),
				request.getDurationInMinutes());

		// if seat class is specified we need to filter out flights searched earlier based on availability of 
		// specified seat class.
		List<Flight> flightsAvailable = null;
		if(request.getSeatClass() != null) {
			flightsAvailable = seatService.getFlightsBySeatClass(request.getSeatClass());
		} else {
			flightsAvailable = seatService.getFlightsByAvailability(true);
		}
		
		HashSet<String> availableFlightIds = new HashSet<>();
		for(Flight flight : flightsAvailable) {
			availableFlightIds.add(flight.getId());
		}
		
		List<Flight> resultFlights = new ArrayList<Flight>();
		for(Flight flight : flights) {
			if(availableFlightIds.contains(flight.getId())) {
				resultFlights.add(flight);
			}
		}
		
		return resultFlights;
	}
	
	//Search flight using destination, source, start date, end date, duration
	@PostMapping("v1/api/flight/search")
	public ResponseEntity<SearchResult> search(@Valid @RequestBody FlightSearchRequest request, BindingResult result) {
		if(result.hasErrors()) {
			SearchResult response = new SearchResult(result.getFieldErrors(), null, null, "Errors found during validation");
			return new ResponseEntity<SearchResult>(response, HttpStatus.BAD_REQUEST);
		}
		
		try { 
			List<Flight> sourceToDestinationFlights = null, destinationToSourceFlights = null;
			sourceToDestinationFlights = getFlights(request);

			if(request.getRoundTripRequest() != null) {
				destinationToSourceFlights = getFlights(request.getRoundTripRequest());
			}
			
			SearchResult searchResult = new SearchResult(null, sourceToDestinationFlights, destinationToSourceFlights, "Successful search");
			return new ResponseEntity<SearchResult>(searchResult, HttpStatus.OK);
		}
		catch(Exception e) {
			return internalServerErrorResponseForSearch;
		}
	}
	
	//search flight by using cities 
	@PostMapping("v1/api/flight/searchByCities")
	public ResponseEntity<SearchResult> searchByCities(@Valid @RequestBody FlightSearchRequestByCities request, BindingResult result) {
		if(result.hasErrors()) {
			SearchResult response = new SearchResult(result.getFieldErrors(), null, null, "Errors found during validation");
			return new ResponseEntity<SearchResult>(response, HttpStatus.BAD_REQUEST);
		}
		
		try { 
			List<Flight> flights = flightService.getfindFlightBetweenCities(request.getSource(), 
					request.getDestination()); 
				
			SearchResult searchResult = new SearchResult(null, flights, null, "Successful search");
			return new ResponseEntity<SearchResult>(searchResult, HttpStatus.OK);
		}
		catch(Exception e) {
			return internalServerErrorResponseForSearch;
		}
	}
	
	//Search flight by using Date & Time
	@PostMapping("v1/api/flight/searchByDateAndTime")
	public ResponseEntity<SearchResult> searchByDateAndTime(@Valid @RequestBody FlightSearchRequestByDateAndTime request, BindingResult result) {
		if(result.hasErrors()) {
			SearchResult response = new SearchResult(result.getFieldErrors(), null, null, "Errors found during validation");
			return new ResponseEntity<SearchResult>(response, HttpStatus.BAD_REQUEST);
		}
		
		try { 
			List<Flight> flights = flightService.getfindFlightBetweenDateAndTime(request.getStart(), 
					request.getEnd()); 
				
			SearchResult searchResult = new SearchResult(null, flights, null, "Successful search");
			return new ResponseEntity<SearchResult>(searchResult, HttpStatus.OK);
		}
		catch(Exception e) {
			return internalServerErrorResponseForSearch;
		}
	}
	
	//Search flight By using SeatClass
	@GetMapping("v1/api/flight/searchBySeatClass")
	public ResponseEntity<SearchResult> searchFlightBySeatSeatClass(@NotEmpty @RequestParam SeatClass seatClass) {
		try { 
			
			List<Flight> flights = seatService.getFlightsBySeatClass(seatClass);
			return new ResponseEntity<SearchResult>(new SearchResult(null, flights, null, 
					"Successfully got the flight."), HttpStatus.OK);
		}
		catch(Exception e) {
			return internalServerErrorResponseForSearch;
		}
	}
	
	
	
}
