package com.flightbooking.entity;

public enum SecurityQuestion {
	BIRTHCITY("What is your birth city?"),
	PETNAME("What is your pet's name?"),
	MOTHERMAIDENNAME("What is your mother's maiden name?");
	
	private String question;
	
	SecurityQuestion(String question) {
		this.question = question;
	}
}
