package com.flightbooking.serviceImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightbooking.entity.Passenger;
import com.flightbooking.repository.PassengerRepository;

@Service
public class PassengerServiceImpl implements PassengerService {
	PassengerRepository passengerRepository;
	
	@Autowired
	public PassengerServiceImpl(PassengerRepository passengerRepository) {
		this.passengerRepository = passengerRepository;
	}
	
	@Override
	public Passenger getPassengerById(Long passengerId) throws Exception {
		return passengerRepository.findById(passengerId).get();
	}

	@Override
	public boolean deletePassengerById(Long passengerId) throws Exception {
		passengerRepository.deleteById(passengerId);
		return true;
	}

	@Override
	public boolean updatePassenger(Passenger passenger) throws Exception {
		passengerRepository.save(passenger);
		return false;
	}

	@Override
	public boolean createPassenger(Passenger passenger) throws Exception {
		passengerRepository.save(passenger);
		return true;
	}

	@Override
	public boolean createPassengers(List<Passenger> passengers) throws Exception {
		passengerRepository.saveAll(passengers);
		return true;
	}
}
