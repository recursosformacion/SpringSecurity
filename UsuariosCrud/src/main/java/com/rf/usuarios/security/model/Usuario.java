package com.rf.usuarios.security.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="Usuario")
public class Usuario {

	@Id
	@SequenceGenerator(name = "idUsuario", sequenceName = "usuarioSEQ", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator="idUsuario") 
	private Long idUsuario;

	private String usNombre;
	
	private String usEmail;
	
	@Column(unique=true)
	private String usUsername;

	private String usPassword;

	public Usuario() {
		super();

	}

	public Usuario(Long idUsuario, String usNombre, String usEmail, String usUsername, String usPassword) {
		super();
		this.idUsuario = idUsuario;
		this.usNombre = usNombre;
		this.usEmail = usEmail;
		this.usUsername = usUsername;
		this.usPassword = usPassword;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUsNombre() {
		return usNombre;
	}

	public void setUsNombre(String usNombre) {
		this.usNombre = usNombre;
	}

	public String getUsEmail() {
		return usEmail;
	}

	public void setUsEmail(String usEmail) {
		this.usEmail = usEmail;
	}

	public String getUsUsername() {
		return usUsername;
	}

	public void setUsUsername(String usUsername) {
		this.usUsername = usUsername;
	}

	public String getUsPassword() {
		return usPassword;
	}

	public void setUsPassword(String usPassword) {
		this.usPassword = usPassword;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", usNombre=" + usNombre + ", usEmail=" + usEmail
				+ ", usUsername=" + usUsername + ", usPassword=" + usPassword + "]";
	}
	
}
