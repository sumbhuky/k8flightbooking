package com.flightbooking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Seat {
	
	@Id
	@Column(name = "seat_id")
	private String seatId;
	
	@Column(name = "seat_class")
	@Enumerated(EnumType.STRING)
	private SeatClass seatClass;
	
	@Column(name = "seat_number")
	private String seatNumber;
	
	@ManyToOne
	@JoinColumn(name = "flight_id")
	@JsonIgnore
	private Flight flight;
	
	@Column(name = "available")
	private boolean available = true;

	public Seat() {}
	
	
	public Seat(SeatClass seatClass, String seatNumber, double price) {
		super();
		this.seatClass = seatClass;
		this.seatNumber = seatNumber;
		
	}


	public SeatClass getSeatClass() {
		return seatClass;
	}
	
	public void setSeatClass(SeatClass seatClass) {
		this.seatClass = seatClass;
	}
	
	public String getSeatNumber() {
		return seatNumber;
	}
	
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}

	public Flight getFlight() {
		return flight;
	}


	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	
	public boolean getAvailable() {
		return available;
	}


	public void setAvailable(boolean available) {
		this.available = available;
	}
}
