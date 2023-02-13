package com.flightbooking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Passenger {
	
	@Id
	@Column(name = "passenger_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long passengerId;
	
	@Column(name = "first_name")
	@NotEmpty
	private String firstName;
	
	@Column(name = "last_name")
	@NotEmpty
	private String lastName;
	
	@Column(name = "age")
	@NotNull
	private Integer age;
	
	@NotEmpty
	@Pattern(regexp="(^$|[0-9]{10})")
	@Column(name = "contact")
	private String contactNumber;
	
	@Email
	@NotEmpty
	@Column(name = "email")
	private String email;

	@OneToOne
	@JoinColumn(name = "seat_id")
	private Seat seat;
	
	@ManyToOne
	@JoinColumn(name = "booking_id")
	@JsonIgnore
	private Booking booking;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public long getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(long passengerId) {
		this.passengerId = passengerId;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}
}
