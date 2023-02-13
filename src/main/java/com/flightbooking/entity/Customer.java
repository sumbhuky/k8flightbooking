package com.flightbooking.entity;

package com.flightbooking.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

@Entity
public class Customer {
	
	@Column(name="firstname", nullable=true)
    private String firstName;

    @Column(name= "lastname", nullable=true)
    private String lastName;
    
    @Column(name="dob", nullable=true)
    private String dateOfBirth;

	@Column(name="email", nullable=false)
	@NotEmpty(message = "email must be specified")
	@Email(message = "not a well formed email")
    private String email;
    
    @Id
    @Column(name="username", nullable=false)
    @NotEmpty(message = "username must be specified")
    private String username;
    
    @Column(name="password", nullable=false)
    @NotEmpty(message = "password must be specified")
	private String password;
    
    @Column(name="role", nullable=false)
    @NotNull(message = "role must be specified")
    @Enumerated(EnumType.STRING)
    private UserRole role;
    
    @Type(type = "serializable")
    @Column(name = "questionanswers", nullable=true)
    private List<SecurityQuestionAnswer> questionAnswers;

	public Customer(int id, String firstName, String lastName, String dateOfBirth, String email, String username, String password,
    		UserRole role) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.username = username;
		this.password = password;
		this.role = role;
	}

    public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public Customer() {
		super();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	   
    public List<SecurityQuestionAnswer> getQuestionAnswers() {
		return questionAnswers;
	}

	public void setQuestionAnswers(List<SecurityQuestionAnswer> questionAnswers) {
		this.questionAnswers = questionAnswers;
	}
	
}

