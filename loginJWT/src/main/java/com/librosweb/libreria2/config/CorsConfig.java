package com.librosweb.libreria2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * Configuración global de CORS para permitir el acceso entre dominios,
 * (como Angular) hacia el backend Spring Boot.
 */
@Configuration
public class CorsConfig {
	
	 @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**") // permite todos los endpoints
	                        .allowedOrigins("http://localhost:4200") // Angular
	                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") //Metodos http permitidos
	                        .allowedHeaders("*")//Permite cualquier encabezado
	                        .allowCredentials(true);//Permite envio de cookies/autenticación.
	            }
	        };
	    }
}
