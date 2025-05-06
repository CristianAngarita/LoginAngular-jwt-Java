package com.librosweb.libreria2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.librosweb.libreria2.user.UserRepository;

//Clase que define beans de configuracion para Spring
@Configuration
public class ApplicationConfig {

	private final UserRepository userRepository;

	public ApplicationConfig(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	/*
	 * Bean que proporciona el AuthenticationManager desde la configuración de
	 * Spring Security. Este se encarga de coordinar el proceso de autenticación.
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	/*
	 * Bean que define el proveedor de autenticación. Usa DaoAuthenticationProvider
	 * para autenticar usuarios desde la base de datos.
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailService());// servicio que busca el usuario
		authenticationProvider.setPasswordEncoder(passwordEncoder());// codifica la contraseña
		return authenticationProvider;
	}

	// Se definie el codificador de contraseñas
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Se usa para cargar un usuario desde la base de datos por su nombre de
	// usuario.
	@Bean
	public UserDetailsService userDetailService() {

		return username -> userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
}
