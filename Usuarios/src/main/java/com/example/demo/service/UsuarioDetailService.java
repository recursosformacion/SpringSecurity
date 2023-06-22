package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.model.Usuario;
import com.example.demo.model.UsuarioSec;
import com.example.demo.repository.UsuarioRepo;

public class UsuarioDetailService implements UserDetailsService{

	@Autowired
	UsuarioRepo usuarioRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> user = Optional.of(new Usuario());
		try {
		user = usuarioRepo.findByUsername(username);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if (user.isEmpty()) {
			throw new UsernameNotFoundException(username);
		}
		return new UsuarioSec(user.get());

	}

}
