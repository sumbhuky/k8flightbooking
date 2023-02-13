package com.flightbooking.entity;

import java.io.Serializable;

public class SecurityQuestionAnswer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private SecurityQuestion question;
	private String answer;
	
	public SecurityQuestionAnswer() {}
	
	
	public SecurityQuestionAnswer(SecurityQuestion question, String answer) {
		super();
		this.question = question;
		this.answer = answer;
	}


	public SecurityQuestion getQuestion() {
		return question;
	}
	
	public void setQuestion(SecurityQuestion question) {
		this.question = question;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
