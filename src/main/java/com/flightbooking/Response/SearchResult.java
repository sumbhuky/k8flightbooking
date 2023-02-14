package com.flightbooking.Response;

import java.util.List;

import org.springframework.validation.FieldError;

import com.flightbooking.entity.Flight;

public class SearchResult {
	private List<FieldError> validationErrors;
	private List<Flight> sourceToDestinationFlights;
	private List<Flight> destinationToSourceFlights;
	
	private String message;
	
	public SearchResult() {}	

	public SearchResult(List<FieldError> validationErrors, List<Flight> sourceToDestinationFlights,
			List<Flight> destinationToSourceFlights, String message) {
		super();
		this.validationErrors = validationErrors;
		this.sourceToDestinationFlights = sourceToDestinationFlights;
		this.destinationToSourceFlights = destinationToSourceFlights;
		this.message = message;
	}

	public List<FieldError> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(List<FieldError> validationErrors) {
		this.validationErrors = validationErrors;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public List<Flight> getSourceToDestinationFlights() {
		return sourceToDestinationFlights;
	}

	public void setSourceToDestinationFlights(List<Flight> sourceToDestinationFlights) {
		this.sourceToDestinationFlights = sourceToDestinationFlights;
	}

	public List<Flight> getDestinationToSourceFlights() {
		return destinationToSourceFlights;
	}

	public void setDestinationToSourceFlights(List<Flight> destinationToSourceFlights) {
		this.destinationToSourceFlights = destinationToSourceFlights;
	}
}
