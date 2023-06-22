package com.example.demo.model;

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
	@Column(unique=true)
	private String username;

	private String password;

	public Usuario() {
		super();

	}
	public Usuario(Long id_usuario, String us_nombre, String username, String password) {
		super();
		this.id_usuario = id_usuario;
		this.us_nombre = us_nombre;
		this.username = username;
		this.password = password;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Usuario [id_usuario=" + id_usuario + ", us_nombre=" + us_nombre + ", username=" + username
				+ ", password=" + password + "]";
	}


}
