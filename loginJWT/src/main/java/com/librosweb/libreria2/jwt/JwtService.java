package com.librosweb.libreria2.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/*
 * Serivio encargado de la generación, validación y análisis de tokens Jwt. 
 * */
@Service
public class JwtService {

	// Clave Secreta sin codificar en base 64.
	private static final String SECRET = "12345678901234567890123456789012"; // Mínimo 256 bits (32 caracteres para
																				// HS256)

	/*
	 * Se usa cuando SECRET esta codificada en base 64 private SecretKey getKey() {
	 * byte[] keyBytes = Decoders.BASE64.decode(SECRET); return
	 * Keys.hmacShaKeyFor(keyBytes); }
	 */

	/*
	 * Método que genera una SecretKey, con una cadena sin codificar que se
	 * convierte en bytes usando utf-8.
	 */
	private SecretKey getKey() {
		return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
	}

	public String getToken(UserDetails user) {
		return getToken(Map.of(), user); // Versión inmutable moderna, a un token con un mapa vacío de claims
											// adicionales.
	}

	/*
	 * Genera un Jwt para usuario incluyendo claims adicionales
	 */
	public String getToken(Map<String, Object> extraClaims, UserDetails user) {
		return Jwts.builder().claims(extraClaims) // Claims personalizados
				.subject(user.getUsername()) // Subject del token (username)
				.issuedAt(new Date(System.currentTimeMillis())) // Fecha de emision
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // Tiempo de expiración (24minutos)
				.signWith(getKey(), Jwts.SIG.HS256) // Firma con HS256 y la clave secreta
				.compact(); // Construye el token final.
	}

	// Extrae el nombre de usuario desde el token.
	public String getUsernameFromToken(String token) {
		return getClaim(token, Claims::getSubject);
	}

	/*
	 * Valida el token de usuario y que no este expirado. (Resuelve con true o
	 * false)
	 */
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	// Extrae todos los claims del token firmado.
	private Claims getAllclaims(String token) {
		return Jwts.parser().verifyWith(getKey()) // Define la clave con la que se verificará la firma del token
				.build() // construye el parser
				.parseSignedClaims(token) // analisa el token
				.getPayload(); // Extrae el contenido del token
	}

	/*
	 * Permite extrae claim especifico. El tipo T se resuelve al tipo del valor que
	 * devuelve claimsResolver
	 */
	public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllclaims(token); // Extrae todos los claims del payload del token.
		return claimsResolver.apply(claims); // ClaimsResolver procesa el objeto claims y devuelve el valor del claim
												// solicitado en el tipo t.

	}

	/*
	 * Extrae la fecha de expiración del token.
	 */
	private Date getExpiration(String token) {
		return getClaim(token, Claims::getExpiration);
	}

	/*
	 * Método que permtite verificar si el token expiró.
	 */
	private boolean isTokenExpired(String token) {
		return getExpiration(token).before(new Date());
	}

}
