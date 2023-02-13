package com.flightbooking.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.flightbooking.entity.Flight;
import com.flightbooking.exception.FlightAlreadyExistsException;
import com.flightbooking.exception.FlightNotFoundException;
import com.flightbooking.response.Response;
import com.flightbooking.response.SearchResult;
import com.flightbooking.service.FlightService;
import com.flightbooking.service.SeatService;

public class FlightController {
	@Autowired
	FlightService flightService;
	
	@Autowired
	SeatService seatService;
	
	private static ResponseEntity<Response> flightAddedResponse 
	= new ResponseEntity<Response>(new Response(null, "Flight added."), HttpStatus.CREATED);

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
	
	
	@PostMapping("v1/api/flight")
	public ResponseEntity<Response> addFlight(@Valid @RequestBody Flight flight, BindingResult result) {
		if(result.hasErrors()) {
			Response response = new Response(result.getFieldErrors(), "Errors found during validation");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
		
		try { 
			flightService.addFlight(flight);
			return flightAddedResponse;
		}
		catch(FlightAlreadyExistsException e) {
			return flightAlreadyExistsExceptionResponse;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return internalServerErrorResponse;
		}
	}
	
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
}
