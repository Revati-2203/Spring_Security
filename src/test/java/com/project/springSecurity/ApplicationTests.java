package com.project.springSecurity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.springSecurity.entities.User;
import com.project.springSecurity.services.JwtService;

@SpringBootTest
class ApplicationTests {
	
	@Autowired
	private JwtService jwtService;
	
	@Test
	void contextLoads() {
		User user = new User(4L, "revati@gmail.com","revati" ,"P@ss");
		
		String token = jwtService.generateAccessToken(user);
		
		System.out.println(token);
		
		Long id = jwtService.getUserIdFromToken(token);
		System.out.println(id);
	}
}
