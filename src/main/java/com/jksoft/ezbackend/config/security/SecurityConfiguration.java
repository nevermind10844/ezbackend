package com.jksoft.ezbackend.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
// @formatter:off
		http
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers("/login").permitAll()
						.requestMatchers("/signup", "/signup/**").permitAll()
						.requestMatchers("/scripts/**", "/styles/**", "/webjars/**").permitAll()
						.anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults()).formLogin(Customizer.withDefaults());
// @formatter:on

		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
