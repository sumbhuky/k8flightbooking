package com.flightbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.Response.Response;
import com.flightbooking.entity.Seat;

import com.flightbooking.service.SeatService;

@RestController
public class SeatController {

	@Autowired
	SeatService seatService;

	// Admin Part
	// Update or Add the seat By Using Flight Id
	@PostMapping("v1/api/{flightId}/seat")
	public ResponseEntity<Response> addSeat(@RequestBody Seat seat, @PathVariable String flightId) {
		seatService.addSeat(seat, flightId);
		return new ResponseEntity<Response>(new Response(null, "Seat added."), HttpStatus.CREATED);

	}

	// Admin Part
	// Getting Seat Details by Using Flight Id
	@GetMapping("v1/api/seat/{seatId}")
	public Seat getSeat(@PathVariable String seatId) {

		return seatService.getSeatBySeatId(seatId);
	}

	// Admin Part
	// Delete the Seat By Using Seat Id
	@DeleteMapping("v1/api/seat/{seatId}")
	public ResponseEntity<Response> deleteSeat(@PathVariable String seatId) {
		seatService.deleteSeat(seatId);
		return new ResponseEntity<Response>(new Response(null, "Seat deleted"), HttpStatus.OK);
	}
}
