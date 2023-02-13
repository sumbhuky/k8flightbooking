package com.flightbooking.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.flightbooking.entity.SecurityQuestionAnswer;

public class PasswordResetRequestUsingSecurityQuestion {
	@NotEmpty
	private String customerId;
	
	@NotEmpty
	private String newPassword;
	
	@NotNull
	private SecurityQuestionAnswer securityQuestionAnswer;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public SecurityQuestionAnswer getSecurityQuestionAnswer() {
		return securityQuestionAnswer;
	}

	public void setSecurityQuestionAnswer(SecurityQuestionAnswer securityQuestionAnswer) {
		this.securityQuestionAnswer = securityQuestionAnswer;
	}
}
