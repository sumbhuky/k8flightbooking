package com.flightbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightbooking.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String>  {
}
