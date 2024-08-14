package com.jksoft.ezbackend.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
						.requestMatchers("/setup", "/setup/**").permitAll()
						.requestMatchers("/favicon*").permitAll()
						.requestMatchers("/").permitAll()
						.requestMatchers("/scripts/**", "/styles/**", "/webjars/**").permitAll()
						.requestMatchers("/instance/company", "/instance/company/", "/instance/company/**").hasRole("INSTANCEADMIN")
						.requestMatchers("/admin/company", "/admin/company/", "/admin/company/**").hasRole("ADMIN")
						.requestMatchers("/company").hasRole("USER")
						.anyRequest().hasAnyRole("USER"))
				.httpBasic(Customizer.withDefaults())
				.formLogin(formLogin -> formLogin
						.loginPage("/login")
						.usernameParameter("email")
						.passwordParameter("password"));
// @formatter:on

		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
