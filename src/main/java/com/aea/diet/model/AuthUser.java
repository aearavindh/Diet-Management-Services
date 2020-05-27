package com.aea.diet.model;

public class AuthUser {
	
	private String username;
	private String password;
	private String role;
	
	public AuthUser(String username, String password, String role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}
	
	

}
