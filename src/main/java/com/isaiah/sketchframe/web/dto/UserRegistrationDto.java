package com.isaiah.sketchframe.web.dto;
//This class was made strictly to allow encryption of the password because BcryptPasswordEncoder needs access to the value while user is being saved
public class UserRegistrationDto {
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private String username;
	
	
	public UserRegistrationDto(){
		
	}
	public UserRegistrationDto(String firstName, String lastName, String email, String password, String username) {
		super();
		this.firstname = firstName;
		this.lastname = lastName;
		this.email = email;
		this.password = password;
		this.username = username;
		
	}

	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstName) {
		this.firstname = firstName;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastName) {
		this.lastname = lastName;
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
	//	Allows Bcrypt access to password field during validation
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
