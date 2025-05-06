package com.librosweb.libreria2.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.librosweb.libreria2.jwt.JwtService;
import com.librosweb.libreria2.user.Rol;
import com.librosweb.libreria2.user.User;
import com.librosweb.libreria2.user.UserRepository;

/*
 * Servicio que maneja la lógica de autenticación (login) y registro de usuarios.
 */
@Service
public class AuthService {
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager) {
		super();
		this.userRepository = userRepository;
		this.jwtService = jwtService;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}

	// Método que realiza el login: autentica al usuario y genera un token JWT.
	public AuthResponse login(LoginRequest request) {
		// Autentica al usuario usando el AuthenticationManager
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		/*
		 * Si no lanza excepción, el usuario es válido Buscamos al usuario desde la base
		 * de datos
		 */
		UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();

		// Generamos el token JWT para este usuario
		String token = jwtService.getToken(user);

		// Devolvemos el token como respuesta
		return new AuthResponse(token);
	}

	// Método que registra un nuevo usuario y devuelve su JWT.
	public AuthResponse register(RegisterRequest request) {
		// Creamos una nueva instancia de User con los datos del request
		User user = new User(request.getUsername(), request.getLastname(), request.getFirstname(), request.getCountry(),
				passwordEncoder.encode(request.getPassword()), Rol.USER);
		// Guardamos el usuario en la base de datos
		userRepository.save(user);
		// Generamos y retornamos un token JWT para este nuevo usuario
		return new AuthResponse(jwtService.getToken(user));
	}

}
