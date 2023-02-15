package com.flightbooking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.flightbooking.entity.Customer;
import com.flightbooking.exception.UserNameIsAlreadyTakenException;
import com.flightbooking.exception.UserNotFoundException;
import com.flightbooking.exception.WrongPasswordException;
import com.flightbooking.repository.CustomerRepository;
import com.flightbooking.request.LoginRequest;
import com.flightbooking.serviceImplementation.CustomerServiceImpl;

@SpringBootTest
public class CustomerServiceImplTest {
	
	@Mock
	CustomerRepository customerRepository;
	
	@InjectMocks
	private CustomerService customerService = new CustomerServiceImpl();
	
	//  testing the sign Up Customer method with junit and mock data
	@Test
    void signUpCustomerSuccessTest() {
		String userName = "user1";
		Optional<Customer> noExistingCustomer = Optional.empty();
		Customer customer = new Customer();
		customer.setUsername(userName);
		when(customerRepository.findById(userName)).thenReturn(noExistingCustomer);
		when(customerRepository.save(customer)).thenReturn(customer);
		
		try {
			assertTrue(customerService.signUpCustomer(customer), "Signup customer should return true");
		} catch(Exception e) {
			fail("Exception should not be thrown");
		}
    }
	
	// testing that if the user name is taken or not 
	@Test
    void signUpCustomerUserNameIsAlreadyTakenExceptionTest() throws Exception {
		String userName = "user2";
		Customer customer = new Customer();
		customer.setUsername(userName);
		Optional<Customer> existingCustomer = Optional.of(customer);
		when(customerRepository.findById(userName)).thenReturn(existingCustomer);
		
		Exception exception = assertThrows(UserNameIsAlreadyTakenException.class, () -> {
			customerService.signUpCustomer(customer);
	    });
		
		assertEquals(exception.getMessage(), "Customer already exists.");
    }
//testing for login customer successfull	
	@Test
    void loginCustomerSuccessTest() {
		String userName = "user3";
		String password = "pass3";
		
		Customer customer = new Customer();
		customer.setUsername(userName);
		customer.setPassword(password);
		
		Optional<Customer> existingCustomer = Optional.of(customer);
		
		when(customerRepository.findById(userName)).thenReturn(existingCustomer);
		
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername(userName);
		loginRequest.setPassword(password);
		
		try {
			assertTrue(customerService.logInCustomer(loginRequest), "Customer login should be successful");
		} catch(Exception e) {
			fail("Exception should not be thrown");
		}
    }
	
	//testing of login and finding the user is there or not
	@Test
    void loginCustomerUserNotFoundExceptionTest() {
		String userName = "user4";
		String password = "pass4";

		Optional<Customer> noExistingCustomer = Optional.empty();
		
		when(customerRepository.findById(userName)).thenReturn(noExistingCustomer);
		
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername(userName);
		loginRequest.setPassword(password);
		
		Exception exception = assertThrows(UserNotFoundException.class, () -> {
			customerService.logInCustomer(loginRequest);
	    });
		
		assertEquals(exception.getMessage(), "User doesn't exist.");
    }
	
	// wrong password  exception test
	@Test
    void loginCustomerWrongPasswordExceptionTest() {
		String userName = "user4";
		String password = "pass4";
		String wrongPassword = "wrongPassword";
		
		Customer customer = new Customer();
		customer.setUsername(userName);
		customer.setPassword(password);
		
		Optional<Customer> existingCustomer = Optional.of(customer);
		
		when(customerRepository.findById(userName)).thenReturn(existingCustomer);
		
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername(userName);
		loginRequest.setPassword(wrongPassword);
		
		Exception exception = assertThrows(WrongPasswordException.class, () -> {
			customerService.logInCustomer(loginRequest);
	    });
		
		assertEquals(exception.getMessage(), "Wrong password.");
    }
// Testing for existing customer
	@Test
    void getCustomerByIdSuccessTest() {
		String userName = "user5";
		
		Customer customer = new Customer();
		customer.setUsername(userName);
		
		Optional<Customer> existingCustomer = Optional.of(customer);
		when(customerRepository.findById(userName)).thenReturn(existingCustomer);
		try {
			Customer customerGotFromApi = customerService.getCustomerById(userName);
			assertEquals(customerGotFromApi, customer, "Same customer object should be returned.");
		} catch(Exception e) {
			fail("Exception should not be thrown");
		}
    }
	
//Testing for non-existing customer	
	@Test
    void getCustomerByIdUserNotFoundExceptionTest() {
		String userName = "user6";
		
		Optional<Customer> noExistingCustomer = Optional.empty();
		when(customerRepository.findById(userName)).thenReturn(noExistingCustomer);
		
		Exception exception = assertThrows(UserNotFoundException.class, () -> {
			customerService.getCustomerById(userName);
	    });
		
		assertEquals(exception.getMessage(), "User doesn't exist.");
    }
//Testing for deleting the customer	
	@Test
	void deleteCustomerByIdTest() throws Exception {
		String userName = "user7";
		
		doNothing().when(customerRepository).deleteById(userName);
		assertTrue(customerService.deleteCustomerById(userName), "Delete customer by id should return true");
	}
	
//Testing for updating the customer	
	@Test
	void updateCustomerTest() throws Exception {
		String userName = "user8";
		
		Customer customer = new Customer();
		customer.setUsername(userName);
		
		when(customerRepository.save(customer)).thenReturn(customer);
		assertEquals(customerService.updateCustomer(customer), customer, "Same object should be returned");
	}
}
