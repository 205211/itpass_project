package com.itpass.quiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.formLogin(login -> login                
				.loginPage("/login").permitAll()
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/quiz", true)
				.usernameParameter("userName")
				.passwordParameter("password"))
		.logout(logout -> logout
				.logoutSuccessUrl("/login?logout")
				.invalidateHttpSession(true))
		.authorizeHttpRequests(authz -> authz
				.requestMatchers("/loginAddUser").permitAll()
				.requestMatchers("/addUser").permitAll()
				.requestMatchers("/loginAddResult").permitAll()
				.anyRequest().authenticated());
        return http.build();
    }
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
