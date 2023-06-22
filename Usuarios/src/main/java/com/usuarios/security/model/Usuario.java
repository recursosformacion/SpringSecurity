package com.usuarios.security.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="UsuarioClase")
public class Usuario {

	@Id
	@SequenceGenerator(name = "id_usuario", sequenceName = "usuarioSEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator="id_usuario") 
	private Long id_usuario;

	private String us_nombre;
	
	private String us_email;
	
	@Column(unique=true)
	private String us_username;

	private String us_password;

	public Usuario() {
		super();

	}

	public Usuario(Long id_usuario, String us_nombre, String us_email, String us_username, String us_password) {
		super();
		this.id_usuario = id_usuario;
		this.us_nombre = us_nombre;
		this.us_email = us_email;
		this.us_username = us_username;
		this.us_password = us_password;
	}

	public Long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getUs_nombre() {
		return us_nombre;
	}

	public void setUs_nombre(String us_nombre) {
		this.us_nombre = us_nombre;
	}

	public String getUs_email() {
		return us_email;
	}

	public void setUs_email(String us_email) {
		this.us_email = us_email;
	}

	public String getUs_username() {
		return us_username;
	}

	public void setUs_username(String us_username) {
		this.us_username = us_username;
	}

	public String getUs_password() {
		return us_password;
	}

	public void setUs_password(String us_password) {
		this.us_password = us_password;
	}

	@Override
	public String toString() {
		return "Usuario [id_usuario=" + id_usuario + ", us_nombre=" + us_nombre + ", us_email=" + us_email
				+ ", us_username=" + us_username + ", us_password=" + us_password + "]";
	}
	
}
