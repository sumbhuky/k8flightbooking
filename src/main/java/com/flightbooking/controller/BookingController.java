package com.flightbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.flightbooking.service.BookingService;
import com.flightbooking.service.CustomerService;
import com.flightbooking.service.FlightService;
import com.flightbooking.service.PassengerService;
import com.flightbooking.service.SeatService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.Response.BookingResponse;
import com.flightbooking.Response.BookingsForCustomer;
import com.flightbooking.Response.Response;
import com.flightbooking.entity.Booking;
import com.flightbooking.entity.BookingStatus;
import com.flightbooking.entity.Customer;
import com.flightbooking.entity.Flight;
import com.flightbooking.entity.Passenger;
import com.flightbooking.entity.Seat;
import com.flightbooking.entity.SeatClass;
import com.flightbooking.exception.SeatNotAvailableException;
import com.flightbooking.exception.UserNotFoundException;
import com.flightbooking.repository.BookingRepository;
import com.flightbooking.request.BookingRequest;

import com.flightbooking.service.BookingService;
import com.flightbooking.service.CustomerService;
import com.flightbooking.service.FlightService;
import com.flightbooking.service.PassengerService;
import com.flightbooking.service.SeatService;

@RestController
public class BookingController {
	@Autowired
	BookingService bookingService;

	@Autowired
	CustomerService customerService;

	@Autowired
	FlightService flightService;

	@Autowired
	SeatService seatService;

	@Autowired
	PassengerService passengerService;

	//Search all bookings by bookingId
	@GetMapping("v1/api/booking/{bookingId}")
	public ResponseEntity<Booking> getBooking(@PathVariable Long bookingId) {
		Booking booking = bookingService.getBookingById(bookingId);

		// no need to return all the seats
		booking.getFlight().setSeats(null);

		return new ResponseEntity<Booking>(bookingService.getBookingById(bookingId), HttpStatus.OK);
	}

	//Ticket Booking by User
	@PostMapping("v1/api/booking")
	public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest bookingRequest) {
		List<Seat> seats = null;
		boolean isExceptionEncoutered = false;
		try {
			if (bookingRequest.getSeatIds().size() != bookingRequest.getPassengers().size()) {
				BookingResponse bookingResponse = new BookingResponse();
				bookingResponse.setMessage("Number of passengers not equal to number of seats");
				return new ResponseEntity<BookingResponse>(bookingResponse, HttpStatus.BAD_REQUEST);
			}

			seats = new ArrayList<Seat>();

			Customer customer = customerService.getCustomerById(bookingRequest.getCustomerId());
			List<Passenger> passengers = bookingRequest.getPassengers();
			Flight flight = flightService.getFlightById(bookingRequest.getFlightId());

			int index = 0;
			double totalAmount = 0.0d;

			for (String seatId : bookingRequest.getSeatIds()) {
				Seat seat = seatService.getSeatBySeatId(seatId);
				if (!seat.getAvailable()) {
					throw new SeatNotAvailableException("Seat not available(seatId:seatNumber) => " + seat.getSeatId()
							+ ":" + seat.getSeatNumber());
				}
				// mark seat as not available and save
				seats.add(seat);
				seat.setAvailable(false);
				seatService.updateSeat(seat);

				// set seat for passenger
				passengers.get(index).setSeat(seat);

				index++;

				// update total amount based on seat class
				SeatClass seatClass = seat.getSeatClass();
				if (seatClass == SeatClass.PREMIUM) {
					totalAmount += flight.getPremiumSeatPrice();
				} else if (seatClass == SeatClass.BUSINESS) {
					totalAmount += flight.getBusinessSeatPrice();
				} else {
					totalAmount += flight.getEconomySeatPrice();
				}
			}

			Booking booking = new Booking();
			booking.setCustomer(customer);
			booking.setPassengers(passengers);
			booking.setFlight(flight);
			booking.setTotalAmount(totalAmount);
			booking.setStatus(BookingStatus.CONFIRMED);

			booking = bookingService.createBooking(booking);

			for (Passenger passenger : passengers) {
				passenger.setBooking(booking);
			}

			passengerService.createPassengers(passengers);

			BookingResponse bookingResponse = new BookingResponse();
			bookingResponse.setBookingId(booking.getBookingId());

			// don't want to send all seats, just send the seats that are booked
			flight.setSeats(seats);
			bookingResponse.setFlight(flight);
			bookingResponse.setMessage("Successfully booked the ticket/s.");
			bookingResponse.setPassenger(passengers);
			bookingResponse.setTotalPrice(totalAmount);

			return new ResponseEntity<BookingResponse>(bookingResponse, HttpStatus.CREATED);

		} catch (UserNotFoundException e) {
			isExceptionEncoutered = true;
			BookingResponse response = new BookingResponse();
			response.setMessage("User not found.");
			return new ResponseEntity<BookingResponse>(response, HttpStatus.BAD_REQUEST);
		} catch (SeatNotAvailableException e) {
			isExceptionEncoutered = true;
			BookingResponse response = new BookingResponse();
			response.setMessage(e.getMessage());
			return new ResponseEntity<BookingResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			isExceptionEncoutered = true;
			BookingResponse response = new BookingResponse();
			response.setMessage("Something went wrong.");
			return new ResponseEntity<BookingResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			// mark seats are available if there is an exception
			if (isExceptionEncoutered) {
				for (Seat seat : seats) {
					seat.setAvailable(true);
					seatService.updateSeat(seat);
				}
			}
		}
	}

	//Cancel booking using bookingId
	@PutMapping("v1/api/booking/{bookingId}/cancel")
	public ResponseEntity<Response> cancelBooking(@PathVariable Long bookingId) {
		Booking booking = bookingService.getBookingById(bookingId);

		// mark booking as cancelled and update
		booking.setStatus(BookingStatus.CANCELLED);
		bookingService.updateBooking(booking);

		// mark seats as available and update, as the booking is getting cancelled
		for (Passenger passenger : booking.getPassengers()) {
			passenger.getSeat().setAvailable(true);
			seatService.updateSeat(passenger.getSeat());
		}

		return new ResponseEntity<Response>(new Response(null, "Booking cancelled."), HttpStatus.OK);
	}

	//Retrieving past booking details of customer using customerId
	@GetMapping("v1/api/booking/history")
	public ResponseEntity<BookingsForCustomer> getBookingHistory(@NotEmpty @PathParam("customerId") String customerId) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			Customer customer = customerService.getCustomerById(customerId);

			BookingsForCustomer bookingsForCustomer = new BookingsForCustomer();

			List<Booking> bookings = bookingService.getBookingsByCustomerId(customerId);

			// Don't want to send seats for flight
			for (Booking booking : bookings) {
				booking.getFlight().setSeats(null);
			}

			bookingsForCustomer.setBookings(bookings);
			bookingsForCustomer.setMessage("Successfully got the booking history");

			return new ResponseEntity<BookingsForCustomer>(bookingsForCustomer, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			BookingsForCustomer bookingsForCustomer = new BookingsForCustomer();
			bookingsForCustomer.setMessage("User not found exception");
			return new ResponseEntity<BookingsForCustomer>(bookingsForCustomer, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			BookingsForCustomer bookingsForCustomer = new BookingsForCustomer();
			bookingsForCustomer.setMessage("Something went wrong");
			return new ResponseEntity<BookingsForCustomer>(bookingsForCustomer, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//Retrieving upcoming booking details of customer using cusotmerId
	@GetMapping("v1/api/booking/upcomingBookings")
	public ResponseEntity<BookingsForCustomer> getUpcomingBookings(
			@NotEmpty @PathParam("customerId") String customerId) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			Customer customer = customerService.getCustomerById(customerId);
			List<Booking> bookings = bookingService.getUpcomingBookingsByCustomerId(customerId, new Date());

			// Don't want to send seats for flight
			for (Booking booking : bookings) {
				booking.getFlight().setSeats(null);
			}

			BookingsForCustomer bookingsForCustomer = new BookingsForCustomer();
			bookingsForCustomer.setBookings(bookings);
			bookingsForCustomer.setMessage("Successfully got the upcoming bookings");

			return new ResponseEntity<BookingsForCustomer>(bookingsForCustomer, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			BookingsForCustomer bookingsForCustomer = new BookingsForCustomer();
			bookingsForCustomer.setMessage("User not found exception");
			return new ResponseEntity<BookingsForCustomer>(bookingsForCustomer, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			BookingsForCustomer bookingsForCustomer = new BookingsForCustomer();
			bookingsForCustomer.setMessage("Something went wrong");
			return new ResponseEntity<BookingsForCustomer>(bookingsForCustomer, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//Retrieve all the ticket booking reports
	@GetMapping("/bookingreportadmin")
	public ResponseEntity<BookingsForCustomer> getAllBookings() {
		BookingsForCustomer bookingsForCustomer = new BookingsForCustomer();
		List<Booking> bookings = bookingService.getAllBookings();
		for (Booking booking : bookings) {
			booking.getFlight().setSeats(null);
		}
		bookingsForCustomer.setBookings(bookings);
		bookingsForCustomer.setMessage("Successfully got the booking report generated");
		return new ResponseEntity<BookingsForCustomer>(bookingsForCustomer, HttpStatus.OK);
	}
}
