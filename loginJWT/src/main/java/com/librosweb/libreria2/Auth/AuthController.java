package com.librosweb.libreria2.Auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}
	//Endpooint de login
	@PostMapping("login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
		// Se recibe un JSON con username y password, se convierte a LoginRequest
		// authService.login() devuelve un AuthResponse con el token JWT
		return ResponseEntity.ok(authService.login(request));
	}
	
	//Endpoint para el registro
	@PostMapping(value = "register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
		// Se recibe un JSON con los datos del nuevo usuario, se convierte a RegisterRequest
		// authService.register() guarda el usuario y devuelve un AuthResponse con el token JWT
		return ResponseEntity.ok(authService.register(request)); 
	}
}
