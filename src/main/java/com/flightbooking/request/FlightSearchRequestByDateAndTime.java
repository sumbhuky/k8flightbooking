package com.flightbooking.request;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;


public class FlightSearchRequestByDateAndTime {
	
	@Future(message = "please add the future date")
	@NotNull(message = "start date must be specified")
	private Date start;
	
	@Future(message = "please add the future date")
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


