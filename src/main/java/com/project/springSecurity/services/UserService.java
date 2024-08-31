package com.project.springSecurity.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.springSecurity.dto.LoginDTO;
import com.project.springSecurity.dto.SignUpDTO;
import com.project.springSecurity.dto.UserDTO;
import com.project.springSecurity.entities.User;
import com.project.springSecurity.exceptions.ResourceNotFoundException;
import com.project.springSecurity.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final ModelMapper modelMapper; 
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User with email "+username+" not found"));
	}

	public UserDTO signUp(SignUpDTO signUpDTO) {
		Optional<User> user = userRepository.findByEmail(signUpDTO.getEmail());
		if(user.isPresent()) {
			throw new BadCredentialsException("User already exists "+signUpDTO.getEmail());
		}
		User toCreate = modelMapper.map(signUpDTO,User.class);
		toCreate.setPassword(passwordEncoder.encode(toCreate.getPassword()));
		User saveUser = userRepository.save(toCreate);
		return modelMapper.map(saveUser, UserDTO.class);
	}

}
