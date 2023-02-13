package com.flightbooking.entity;

public enum Location {
	BANGALORE("bangalore", "india", "blr", "ind"),
	MUMBAI("mumbai", "india", "mum", "ind"),
	KOLKATA("kolkata", "india", "kol", "ind"),
	HYDERABAD("hyderabad", "india", "hyd", "ind"),
	CHENNAI("chennai", "india", "che", "ind"),
	DELHI("delhi", "india", "del", "ind"),
	CHANDIGARH("chandigarh", "india", "cha", "ind");	
	
	private String city;
	private String country;
	private String cityCode;
	private String countryCode;
	
	Location(final String city, String country, String cityCode, String countryCode) {
		this.city = city;
		this.country = country;
		this.cityCode = cityCode;
		this.countryCode = countryCode;
	}
}
