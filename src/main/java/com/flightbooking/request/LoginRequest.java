package com.flightbooking.request;

import javax.validation.constraints.NotEmpty;

public class LoginRequest {
	
	@NotEmpty(message = "username must be specified")
	private String username;
	
	@NotEmpty(message = "password must be specified")
	private String password;
	
	public LoginRequest() {}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
