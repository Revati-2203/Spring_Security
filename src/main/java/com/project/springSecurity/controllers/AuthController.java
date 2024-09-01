package com.project.springSecurity.controllers;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.springSecurity.dto.LoginDTO;
import com.project.springSecurity.dto.LoginResponseDTO;
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
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletRequest httpServletRequest, HttpServletResponse response){
		LoginResponseDTO loginResponseDTO  = authService.login(loginDTO);
		
		Cookie cookie = new Cookie("refreshToken", loginResponseDTO.getRefreseToken());
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
		
		return ResponseEntity.ok(loginResponseDTO);
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request){
		String refreshToken = Arrays.stream(request.getCookies())
				.filter(cookie -> "refreshToken".equals(cookie.getName()))
				.findFirst()
				.map(Cookie::getValue)
				.orElseThrow(()-> new AuthenticationServiceException("Refresh token not found inside cookies"));
		
		LoginResponseDTO loginResponseDTO = authService.refreshToken(refreshToken);
		
		return ResponseEntity.ok(loginResponseDTO);
	}
}
