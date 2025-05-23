package com.librosweb.libreria2.Auth;

/*
 * DTO que representa la información necesaria para registrar un nuevo usuario.
 */
public class RegisterRequest {
	String username;
	String password;
	String firstname;
	String lastname;
	String country;
	public RegisterRequest(String username, String password, String firstname, String lastname, String country) {
		super();
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.country = country;
	}
	public RegisterRequest() {
		super();
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
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
}
