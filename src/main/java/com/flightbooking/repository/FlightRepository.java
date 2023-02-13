package com.flightbooking.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.flightbooking.entity.Flight;
import com.flightbooking.entity.Location;

public interface FlightRepository extends JpaRepository<Flight, String> {
    @Query("select f from Flight f where f.source = :source and f.destination = :destination"
    		+ " and f.departure >= :start and f.arrival <= :end and f.durationInMinutes <= :duration")
	List<Flight> genericSearch(@Param("source") Location source, 
			@Param("destination") Location destination, 
			@Param("start")Date start, 
			@Param("end")Date end,
			@Param("duration") Integer duration);
    
    @Query("select f from Flight f where f.source = :source and f.destination = :destination")
    List<Flight> findFlightBetweenCities(@Param("source") Location source, 
			@Param("destination") Location destination);
    
    @Query("select f from Flight f where f.departure >= :start and f.arrival <= :end")
    List<Flight> findFlightBetweenDateAndTime(@Param("start")Date start, 
			@Param("end")Date end);
}