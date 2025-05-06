package com.librosweb.libreria2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.librosweb.libreria2.jwt.JwtAuthenticationFilter;
/*
 * Configura la seguridad de la aplicacion usando security y Jwt
 * */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final AuthenticationProvider authProvider;

	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authProvider) {
		super();
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.authProvider = authProvider;
	}

	// Define cade de filtros de seguridad
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())// Desactiva CSRF (Cross-Site Request Forgery) porque se usa
												// autenticación sin estado (JWT)
				.authorizeHttpRequests(authRequest -> authRequest.// Define reglas de autorización
						requestMatchers("/auth/**").permitAll() // Permite el acceso sin autenticación a todas las rutas
																// que comiencen con /auth/
						.anyRequest().authenticated()) // Cualquier otra ruta requiere autorizacion
				// La sesión será sin estado (no se guarda en servidor)
				.sessionManagement(
						sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authProvider) // usa proveedor de autenticacion definido
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)/*-----
																										 * Agrega el
																										 * filtro JWT
																										 * antes del
																										 * filtro
																										 * estándar de
																										 * autenticación
																										 * por usuario y
																										 * contraseña
																										 */
				.build();//contruye y devuele la cadena de filtros
	
	}
}
