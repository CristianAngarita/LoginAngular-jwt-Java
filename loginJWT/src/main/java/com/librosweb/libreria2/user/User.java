package com.librosweb.libreria2.user;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/*
 *	Entidad user 
 */
@Entity
@Table(name = "user", // Nombre de la tabla de la base de datos
		uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) //Garantiza no repetir usuarios
		}
)
public class User implements UserDetails {
	@Id
	@GeneratedValue
	Integer id;
	@Column(nullable = false)//username nunca puede ser nulo.
	String username;
	String lastname;
	String firstname;
	String country;
	String password;
	@Enumerated(EnumType.STRING) //Guarda el enum como texto(En mi caso es USER, ADMIN por eso define String)
	Rol role;

	public User() {
		super();
	}

	public User(Integer id, String username, String lastname, String firstname, String country, String password,
			Rol role) {
		super();
		this.id = id;
		this.username = username;
		this.lastname = lastname;
		this.firstname = firstname;
		this.country = country;
		this.password = password;
		this.role = role;
	}

	public User(String username, String lastname, String firstname, String country, String password, Rol role) {
		super();
		this.username = username;
		this.lastname = lastname;
		this.firstname = firstname;
		this.country = country;
		this.password = password;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Rol getRole() {
		return role;
	}

	public void setRole(Rol role) {
		this.role = role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return List.of(new SimpleGrantedAuthority((role.name())));
	}

}
