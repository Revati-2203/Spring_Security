package com.project.springSecurity.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.springSecurity.dto.LoginDTO;
import com.project.springSecurity.dto.SignUpDTO;
import com.project.springSecurity.dto.UserDTO;
import com.project.springSecurity.services.AuthService;
import com.project.springSecurity.services.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	
	private final UserService userService;
	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO){
		UserDTO userDTO = userService.signUp(signUpDTO);
		return ResponseEntity.ok(userDTO);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpServletRequest httpServletRequest, HttpServletResponse response){
		String token = authService.login(loginDTO);
		
		Cookie cookie = new Cookie("token", token);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
		
		return ResponseEntity.ok(token);
	}
}
