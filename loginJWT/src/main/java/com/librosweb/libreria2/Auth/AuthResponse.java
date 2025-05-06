package com.librosweb.libreria2.Auth;
/*
 * DTO que representa la respuesta al usuario después de un login o registro exitoso.
 * Contiene el token JWT que se usará en las solicitudes futuras.
 */
public class AuthResponse {
	String  token;

	public AuthResponse() {
		super();
	}

	public AuthResponse(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
