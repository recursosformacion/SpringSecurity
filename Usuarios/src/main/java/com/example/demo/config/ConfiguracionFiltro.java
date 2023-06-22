package com.example.demo.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ConfiguracionFiltro {

	@Bean
	SecurityFilterChain filtro(HttpSecurity http) throws Exception {
		http
		    .csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(authorize -> authorize
//				.requestMatchers(HttpMethod.POST, "/api/usuario").permitAll()
//				.requestMatchers(HttpMethod.GET, "/api/*").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/usuario/view").authenticated()
				.requestMatchers(HttpMethod.PUT, "/api/*").authenticated()
				.requestMatchers(HttpMethod.DELETE, "/api/*").authenticated()
				.anyRequest().permitAll()
				)
		.httpBasic(withDefaults());

		return http.build();
	}
}
