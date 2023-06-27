package com.rf.usuarios.security.model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioSec implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	private Usuario user;
	private String username;
	private String password;
	private String token;

	public UsuarioSec(Usuario user){
		this.user = user;
		this.username=user.getUsUsername();
		this.password=user.getUsPassword();
	}
	

	public UsuarioSec() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		roles.add(new SimpleGrantedAuthority("ADMIN"));
		return roles;
	}

	@Override
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}



	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}