package com.flightbooking.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flightbooking.entity.Flight;
import com.flightbooking.entity.Seat;
import com.flightbooking.entity.SeatClass;

@Repository

public interface SeatRepository extends JpaRepository<Seat, String>  {
	@Query("select s from Seat s where s.seatNumber = :seatNumber and s.id = :flightId")
	Seat getSeatBySeatNumbersAndFlightId(@Param("seatNumber") String seatNumber, @Param("flightId") String flightId);
	
	@Query("select DISTINCT s.flight from Seat s where s.seatClass = :seatClass and s.available = true")
	List<Flight> getFlightsBySeatClass(@Param("seatClass") SeatClass seatClass);
	
	@Query("select DISTINCT s.flight from Seat s where s.available = :available")
	List<Flight> getFlightsBySeatAvailability(@Param("available") Boolean available);
}
