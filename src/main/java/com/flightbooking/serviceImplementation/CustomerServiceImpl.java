package com.flightbooking.serviceImplementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightbooking.entity.Customer;
import com.flightbooking.exception.UserNameIsAlreadyTakenException;
import com.flightbooking.exception.UserNotFoundException;
import com.flightbooking.exception.WrongPasswordException;
import com.flightbooking.repository.CustomerRepository;
import com.flightbooking.request.LoginRequest;
import com.flightbooking.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	 
	@Override
	public boolean signUpCustomer(Customer customer) throws Exception {
		Optional<Customer> currentCustomer = customerRepository.findById(customer.getUsername());
		
		if(!currentCustomer.isEmpty()) {
			throw new UserNameIsAlreadyTakenException();
		}
			
		customerRepository.save(customer);
		return true;
	}

	@Override
	public boolean logInCustomer(LoginRequest loginRequest) throws Exception {
		Optional<Customer> customer = customerRepository.findById(loginRequest.getUsername());
		
		if(customer.isEmpty()) {
			throw new UserNotFoundException();
		}
			
		String password = customer.get().getPassword();
		if(password == null || ! password.equals(loginRequest.getPassword())) {
			throw new WrongPasswordException();
		}
		
		return true;
	}

	@Override
	public Customer getCustomerById(String customerId) throws Exception {
		Optional<Customer> customer =  customerRepository.findById(customerId);
		
		if(customer.isEmpty()) {
			throw new UserNotFoundException();
		}
		
		return customer.get();
	}

	@Override
	public boolean deleteCustomerById(String customerId) throws Exception {
		customerRepository.deleteById(customerId);
		return true;
	}

	@Override
	public Customer updateCustomer(Customer customer) throws Exception {
		return customerRepository.save(customer);
	}
}