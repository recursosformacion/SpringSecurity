package com.rf.usuarios.security.controller.jwt;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.rf.usuarios.security.model.UsuarioSec;
import com.rf.usuarios.security.model.dto.LoginDto;

@RestController
@RequestMapping("/api/auth")
public class jwtController {
	
	@Value("${com.recursosformacion.secretKey}")
	private String SECRET_KEY;
	
	@Value("${com.recursosformacion.jwtId}")
	private String jwtId;
	
	@Value("${com.recursosformacion.roles}")
	private String rolesLit;
	
	@PostMapping("/login")
	public UsuarioSec login(@RequestBody LoginDto  login) {
		System.out.println("entrando.....");
		String token = getJWTToken(login.getUsername());
		UsuarioSec user = new UsuarioSec();
		user.setUsername(login.getUsername());
		user.setToken(token);		
		return user;
		
	}

	private String getJWTToken(String username) {
		
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId(jwtId)
				.setSubject(username)
				.claim(rolesLit,
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						SECRET_KEY.getBytes()).compact();

		return "Bearer " + token;
	}
}
