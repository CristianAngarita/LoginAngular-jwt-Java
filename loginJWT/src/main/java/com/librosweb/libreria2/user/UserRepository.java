package com.librosweb.libreria2.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Este repositorio permite el acceso a los datos de la entidad user.
 * Extiende JpaRepositor que trae métodos CRUD para usar.
 */
public interface UserRepository extends JpaRepository<User, Integer>{
	/*
	 * Método que permite buscar un usuario por el username
	 * Optional que contiene el usuario si se encuentra o vacio si no. 
	 * */
	Optional<User> findByUsername(String username);
}
