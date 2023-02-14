package com.flightbooking.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Flight {
	
	@Id
	@Column(name = "id", nullable = false)
	@NotEmpty
	private String id;
	
	@Column(name = "airline", nullable = false)
	@Enumerated(EnumType.STRING)
	private Airline airline;
	
	@Column(name = "source", nullable = false)
	@Enumerated(EnumType.STRING)
	private Location source;
	
	@Column(name = "destination", nullable = false)
	@Enumerated(EnumType.STRING)
	private Location destination;
	
	@Column(name = "departure", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date departure;
	
	@Column(name = "arrival", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date arrival;
	
	@OneToMany(mappedBy = "flight")
	private List<Seat> seats;
	
	@Column(name = "premium_seat_price")
	@NotNull
	private Double premiumSeatPrice;
	
	@Column(name = "business_seat_price")
	@NotNull
	private Double businessSeatPrice;
	
	@Column(name = "economy_seat_price")
	@NotNull
	private Double economySeatPrice;
	
	@Column(name = "duration_in_minutes")
	@NotNull
	private Integer durationInMinutes;

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public Location getSource() {
		return source;
	}

	public void setSource(Location source) {
		this.source = source;
	}

	public Location getDestination() {
		return destination;
	}

	public void setDestination(Location destination) {
		this.destination = destination;
	}

	public Date getDeparture() {
		return departure;
	}

	public void setDeparture(Date departure) {
		this.departure = departure;
	}

	public Date getArrival() {
		return arrival;
	}

	public void setArrival(Date arrival) {
		this.arrival = arrival;
	}

	public Double getPremiumSeatPrice() {
		return premiumSeatPrice;
	}

	public void setPremiumSeatPrice(Double premiumSeatPrice) {
		this.premiumSeatPrice = premiumSeatPrice;
	}

	public Double getBusinessSeatPrice() {
		return businessSeatPrice;
	}

	public void setBusinessSeatPrice(Double businessSeatPrice) {
		this.businessSeatPrice = businessSeatPrice;
	}

	public Double getEconomySeatPrice() {
		return economySeatPrice;
	}

	public void setEconomySeatPrice(Double economySeatPrice) {
		this.economySeatPrice = economySeatPrice;
	}

	public Integer getDurationInMinutes() {
		return durationInMinutes;
	}

	public void setDurationInMinutes(Integer durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}
}
