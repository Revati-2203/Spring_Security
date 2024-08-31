package com.project.springSecurity.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.project.springSecurity.dto.LoginDTO;
import com.project.springSecurity.entities.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	
	public String login(LoginDTO loginDTO) {
		Authentication authenticate=authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
		
		User user = (User) authenticate.getPrincipal();
		return jwtService.generateToken(user);
	}

}
