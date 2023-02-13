package com.flightbooking.Response;

import java.util.List;

import org.springframework.validation.FieldError;

public class Response {
	private List<FieldError> validationErrors;
	private String message;
	
	public Response() {}
	
	public Response(List<FieldError> validationErrors, String message) {
		super();
		this.validationErrors = validationErrors;
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
}
