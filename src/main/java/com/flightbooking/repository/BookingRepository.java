package com.flightbooking.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flightbooking.entity.Booking;
import com.flightbooking.entity.Customer;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>  {
	@Query("select b from Booking b where b.customer.username = :customerId")
	List<Booking> getBookingsByCustomerId(@Param("customerId") String customerId);
	
	@Query("select b from Booking b where b.customer.username = :customerId and b.flight.departure > :currentDate"
			+ " and b.status = 'CONFIRMED'")
	List<Booking> getUpcomingBookingsByCustomerId(@Param("customerId") String customerId, 
			@Param("currentDate") Date currentDate);
}
