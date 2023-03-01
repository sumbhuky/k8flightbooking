package com.flightbooking.request;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.flightbooking.entity.Location;
import com.flightbooking.entity.SeatClass;

public class FlightSearchRequest {
	
	@NotNull(message = "source must be specified")
	private Location source;
	
	@NotNull(message = "destination must be specified")
	private Location destination;
	
	@Future(message = "please add the future date")
	@NotNull(message = "start date must be specified")
	private Date start;
	
	@Future(message = "please add the future date")
	@NotNull(message = "end date must be specified")
	private Date end;

	private SeatClass seatClass; 
	
	private FlightSearchRequest roundTripRequest;
	
	// Default to max value to match all durations using <= operator in query
	private Integer durationInMinutes = Integer.MAX_VALUE;
	
	public FlightSearchRequest() {}
	
	public FlightSearchRequest(@NotNull(message = "source must be specified") Location source,
			@NotNull(message = "destination must be specified") Location destination,
			@NotNull(message = "start date must be specified") Date start,
			@NotNull(message = "end date must be specified") Date end) {
		super();
		this.source = source;
		this.destination = destination;
		this.start = start;
		this.end = end;
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

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public SeatClass getSeatClass() {
		return seatClass;
	}

	public void setSeatClass(SeatClass seatClass) {
		this.seatClass = seatClass;
	}

	public FlightSearchRequest getRoundTripRequest() {
		return roundTripRequest;
	}

	public void setRoundTripRequest(FlightSearchRequest roundTripRequest) {
		this.roundTripRequest = roundTripRequest;
	}

	public Integer getDurationInMinutes() {
		return durationInMinutes;
	}

	public void setDurationInMinutes(Integer durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}	
}
