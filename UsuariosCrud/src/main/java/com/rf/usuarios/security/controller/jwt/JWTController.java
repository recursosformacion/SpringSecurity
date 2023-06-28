package com.rf.usuarios.security.controller.jwt;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rf.usuarios.security.jwtUtil.JwtUtils;
import com.rf.usuarios.security.model.UsuarioSec;
import com.rf.usuarios.security.model.dto.JwtResponse;
import com.rf.usuarios.security.model.dto.LoginDto;

@RestController
@RequestMapping("/api/auth")
public class JWTController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto login) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), 
						                                              login.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		UsuarioSec userDetails = (UsuarioSec) authentication.getPrincipal();
		List<String> roles = userDetails
				                .getAuthorities()
				                .stream()
				                .map(item -> item.getAuthority())
				                .toList();
		String token = jwtUtils.generateTokenFromUsername(login.getUsername());
		userDetails.setToken(token);
		return ResponseEntity.ok(new JwtResponse(token, 
				                                 userDetails.getUser().getIdUsuario(), 
				                                 userDetails.getUsername(),
				                                 userDetails.getUser().getUsEmail(), 
				                                 roles));
	}	
}
