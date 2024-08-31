package com.project.springSecurity.dto;

import lombok.Data;

@Data
public class SignUpDTO {
	
	private Long id;
	private String email;
	private String password;
	private String name;
}
