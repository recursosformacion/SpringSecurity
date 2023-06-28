package com.rf.usuarios.security.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rf.usuarios.security.jwt.JWTAuthorizationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class ConfiguracionFiltro {
	
	@Autowired
	JWTAuthorizationFilter jwtAuthorizationFilter;

	@Bean
	SecurityFilterChain filtro(HttpSecurity http) throws Exception {
		http
		    .csrf(csrf -> csrf.disable())
		    .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
				.requestMatchers(HttpMethod.PUT, "/api/*").authenticated()
				.requestMatchers(HttpMethod.DELETE, "/api/*").authenticated()
				.anyRequest().permitAll()
				)
		.httpBasic(withDefaults());

		return http.build();
	}
}
