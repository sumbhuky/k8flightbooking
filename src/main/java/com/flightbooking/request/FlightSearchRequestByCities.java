package com.flightbooking.request;

import javax.validation.constraints.NotNull;

import com.flightbooking.entity.Location;

public class FlightSearchRequestByCities {
	
	@NotNull(message = "source must be specified")
	private Location source;
	
	@NotNull(message = "destination must be specified")
	private Location destination;

	public FlightSearchRequestByCities() {}
	
	public FlightSearchRequestByCities(@NotNull(message = "source must be specified") Location source,
			@NotNull(message = "destination must be specified") Location destination) {
		super();
		this.source = source;
		this.destination = destination;
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
}
