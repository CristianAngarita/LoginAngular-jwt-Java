package com.librosweb.libreria2.jwt;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * Filtro personalizado que intercepta cada solicitud HTTP una sola vez
 * y autentica al usuario si el token JWT es válido.
 */

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;

	public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
		super();
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	/*
	 * Método que intercepta todas las solicitudes HTTP. Extrae el token JWT, valida
	 * su autenticidad y establece el contexto de seguridad.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Extrae el token del encabezadode la solicitud.
		final String token = getTokenFromRequest(request);
		final String username;

		// Si no hay Token presente, continúa la cade de filtros sin autentixar.
		if (token == null) {
			filterChain.doFilter(request, response);
			return;
		}
		// Extrae nombre usuario desde el token.
		username = jwtService.getUsernameFromToken(token);

		// Si el usuario es valido y no esta autenticado en este contexto
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// Carga el usuario desde la base de datos (u otra fuente)
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			// verifica que el token sea valido para este usuario
			if (jwtService.isTokenValid(token, userDetails)) {
				// Crea token de autenticación y lo asigna al contexto de segurida.
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				// Asocia los detalles de la solicitud (como IP, etc.)
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				// Establece el usuario autenticado en el contexto
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		// Continúa con la cadena de filtros
		filterChain.doFilter(request, response);
	}

	/*
	 * Este método extrae el jwt del encabezado Authorization de la solicitud http
	 */
	private String getTokenFromRequest(HttpServletRequest request) {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		// Verifica que authHeader no sea null y comience con la cadena Bearer.
		if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7); // extrae el token excluyendo Bearer .
		}
		return null;
	}

}
