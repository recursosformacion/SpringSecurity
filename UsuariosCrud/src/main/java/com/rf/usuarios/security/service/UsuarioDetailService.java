package com.rf.usuarios.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.rf.usuarios.security.model.Usuario;
import com.rf.usuarios.security.model.UsuarioSec;
import com.rf.usuarios.security.repository.UsuarioRepo;


public class UsuarioDetailService implements UserDetailsService{

	@Autowired
	UsuarioRepo usuarioRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> user = Optional.of(new Usuario());
		try {
		user = usuarioRepo.findByUsUsername(username);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if (user.isEmpty()) {
			throw new UsernameNotFoundException(username);
		}
		return new UsuarioSec(user.get());

	}

}
