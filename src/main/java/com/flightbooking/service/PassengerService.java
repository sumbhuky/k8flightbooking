package com.flightbooking.service;


import java.util.List;

import com.flightbooking.entity.Passenger;

public interface PassengerService {
	Passenger getPassengerById(Long passengerId) throws Exception;
	boolean deletePassengerById(Long passengerId) throws Exception;
	boolean updatePassenger(Passenger passenger) throws Exception;
	boolean createPassenger(Passenger passenger) throws Exception;
	boolean createPassengers(List<Passenger> passengers) throws Exception;

}