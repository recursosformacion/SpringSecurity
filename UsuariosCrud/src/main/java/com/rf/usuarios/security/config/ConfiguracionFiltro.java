package com.rf.usuarios.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ConfiguracionFiltro {

	@Bean
	SecurityFilterChain filtro(HttpSecurity http) throws Exception {
		http
		    .csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(HttpMethod.PUT, "/api/*").authenticated()
				.requestMatchers(HttpMethod.DELETE, "/api/*").authenticated()
				.anyRequest().permitAll()
				)
		.httpBasic(withDefaults());

		return http.build();
	}
}
