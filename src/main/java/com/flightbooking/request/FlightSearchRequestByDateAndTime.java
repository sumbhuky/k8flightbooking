package com.flightbooking.request;

import java.util.Date;

import javax.validation.constraints.NotNull;


public class FlightSearchRequestByDateAndTime {

	@NotNull(message = "start date must be specified")
	private Date start;
	
	@NotNull(message = "end date must be specified")
	private Date end;

	public FlightSearchRequestByDateAndTime() {}
	
	public FlightSearchRequestByDateAndTime(@NotNull(message = "start date must be specified") Date start,
			@NotNull(message = "end date must be specified") Date end) {
		super();
		this.start = start;
		this.end = end;
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
}


