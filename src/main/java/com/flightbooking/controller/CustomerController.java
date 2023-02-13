package com.flightbooking.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightbooking.entity.Customer;
import com.flightbooking.entity.SecurityQuestionAnswer;
import com.flightbooking.exception.UserNameIsAlreadyTakenException;
import com.flightbooking.exception.UserNotFoundException;
import com.flightbooking.exception.WrongPasswordException;
import com.flightbooking.request.LoginRequest;
import com.flightbooking.request.PasswordResetRequestUsingSecurityQuestion;
import com.flightbooking.response.Response;
import com.flightbooking.service.CustomerService;

@RestController
public class CustomerController {
	
	private static ResponseEntity<Response> userCreatedResponse 
		= new ResponseEntity<Response>(new Response(null, "User created."), HttpStatus.CREATED);
	
	private static ResponseEntity<Response> userUpdatedResponse 
		= new ResponseEntity<Response>(new Response(null, "User updated."), HttpStatus.CREATED);
	
	private static ResponseEntity<Response> userDeletedResponse
		= new ResponseEntity<Response>(new Response(null, "User deleted or does not exist."), HttpStatus.CREATED);
	
	private static ResponseEntity<Response> userNameIsAlreadyTakenResponse 
		= new ResponseEntity<Response>(new Response(null, "User name is already taken."), HttpStatus.BAD_REQUEST);
	
	private static ResponseEntity<Response> internalServerErrorResponse 
		= new ResponseEntity<Response>(new Response(null, "Something went wrong."), HttpStatus.INTERNAL_SERVER_ERROR);
	
	private static ResponseEntity<Response> userNotFoundExceptionResponse 
		= new ResponseEntity<Response>(new Response(null, "User not found."), HttpStatus.BAD_REQUEST);

	private static ResponseEntity<Response> wrongPasswordExceptionResponse 
		= new ResponseEntity<Response>(new Response(null, "Wrong password."), HttpStatus.UNAUTHORIZED);
	
	private static ResponseEntity<Response> loginSuccessResponse 
		= new ResponseEntity<Response>(new Response(null, "Successful login."), HttpStatus.OK);
	
	private static ResponseEntity<Response> passwordUpdateSuccessResponse 
		= new ResponseEntity<Response>(new Response(null, "Successful updated password."), HttpStatus.OK);

	private static ResponseEntity<Response> passwordUpdateFailureResponse 
		= new ResponseEntity<Response>(new Response(null, "Password update failed because couldn't find"
				+ " matching answer."), HttpStatus.BAD_REQUEST);
	
	private static ResponseEntity<Response> passwordUpdateFailureResponseNoQuestionAnswerSet 
		= new ResponseEntity<Response>(new Response(null, "Password update failed because "
				+ " no questions are set for the user."), HttpStatus.BAD_REQUEST);
	
	@Autowired
	CustomerService customerService;
	
	@PostMapping("v1/api/customer/signup")
	public ResponseEntity<Response> signUpCustomer(@Valid @RequestBody Customer customer, BindingResult result) {
		if(result.hasErrors()) {
			Response response = new Response(result.getFieldErrors(), "Errors found during validation");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
		
		try { 
			customerService.signUpCustomer(customer);
			return userCreatedResponse;
		}
		catch(UserNameIsAlreadyTakenException e) {
			return userNameIsAlreadyTakenResponse;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return internalServerErrorResponse;
		}
	}@PostMapping("v1/api/customer/login")
	public ResponseEntity<Response> logInCustomer(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) { 
		if(result.hasErrors()) {
			Response response = new Response(result.getFieldErrors(), "Errors found during validation");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			customerService.logInCustomer(loginRequest);
		} 
		catch(UserNotFoundException e) {
			return userNotFoundExceptionResponse;
		}
		catch(WrongPasswordException e) {
			return wrongPasswordExceptionResponse;
		}
		catch(Exception e) {
			return internalServerErrorResponse;
		}
		
		return loginSuccessResponse;
	}
	
	@PostMapping("v1/api/customer/resetPassword")
	public ResponseEntity<Response> resetPasswordUsingSecurityQuestion(
			@Valid @RequestBody PasswordResetRequestUsingSecurityQuestion request, BindingResult result) {
		if(result.hasErrors()) {
			Response response = new Response(result.getFieldErrors(), "Errors found during validation");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			Customer customer = customerService.getCustomerById(request.getCustomerId());
			List<SecurityQuestionAnswer> questionAnswers = customer.getQuestionAnswers();
			SecurityQuestionAnswer inputQuestionAnswer = request.getSecurityQuestionAnswer();
			
			if(questionAnswers == null || questionAnswers.isEmpty()) {
				return passwordUpdateFailureResponseNoQuestionAnswerSet;
			}
			
			boolean atLeastOneMatched = false;
			for(SecurityQuestionAnswer questionAnswer : questionAnswers) {
				if(inputQuestionAnswer.getQuestion() == questionAnswer.getQuestion()
						&& inputQuestionAnswer.getAnswer().equalsIgnoreCase(questionAnswer.getAnswer())) {
					atLeastOneMatched = true;
					break;
				}
			}
			
			if(atLeastOneMatched) {
				customer.setPassword(request.getNewPassword());
				customerService.updateCustomer(customer);
				return passwordUpdateSuccessResponse;
			} else {
				return passwordUpdateFailureResponse;
			}
		} catch(UserNotFoundException e) {
			return userNotFoundExceptionResponse;
		} catch(Exception e) {
			return internalServerErrorResponse;
		}
	}
