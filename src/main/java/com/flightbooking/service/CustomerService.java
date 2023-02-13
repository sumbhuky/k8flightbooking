package com.flightbooking.service;

import com.flightbooking.entity.Customer;
import com.flightbooking.request.LoginRequest;

public interface CustomerService {
	boolean signUpCustomer(Customer customer) throws Exception;
	boolean logInCustomer(LoginRequest customer) throws Exception;
	Customer getCustomerById(String customerId) throws Exception;
	boolean deleteCustomerById(String customerId) throws Exception;
	Customer updateCustomer(Customer customer) throws Exception;
}