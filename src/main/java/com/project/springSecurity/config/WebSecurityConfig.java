package com.project.springSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
		httpSecurity
		.authorizeHttpRequests(auth -> auth
					.requestMatchers("/posts","/auth/**","/error").permitAll()
					.requestMatchers("/posts/**").hasAnyRole("ADMIN")
					.anyRequest().authenticated())
		.csrf(csrfConfig -> csrfConfig.disable())
		.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.formLogin(Customizer.withDefaults());
		
		return httpSecurity.build();			
	}
	
//	@Bean
//	UserDetailsService myInMemoryUSerDetailsService() {
//		UserDetails	normalUser = User
//				.withUsername("Revati")
//				.password(passwordEncoder().encode("P@ss"))
//				.roles("ADMIN")
//				.build();	
//		UserDetails	adminUser = User
//				.withUsername("admin")
//				.password(passwordEncoder().encode("admin"))
//				.roles("ADMIN")
//				.build();	
//		return new InMemoryUserDetailsManager(normalUser, adminUser);
//	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
