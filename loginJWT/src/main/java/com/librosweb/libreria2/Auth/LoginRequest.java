package com.librosweb.libreria2.Auth;

/*
 * DTO para manejar los datos de inicio de sesión enviados por el cliente.
 */
public class LoginRequest {
	String username;
	String password;
	
	public LoginRequest() {
		super();
	}

	public LoginRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
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
	
	
}
