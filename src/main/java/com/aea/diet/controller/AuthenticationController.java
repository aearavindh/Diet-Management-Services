package com.aea.diet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aea.diet.model.AuthUser;
import com.aea.diet.model.Challenger;
import com.aea.diet.model.Response;
import com.aea.diet.service.AuthenticationService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	
	@PostMapping("/register")
	@ApiOperation("Registration")
	public String register(@RequestBody Challenger challenger) {
		
		return authenticationService.register(challenger);
		
	}
	
	@PostMapping("/login")
	@ApiOperation("Login verification")
	public String login(@RequestBody AuthUser authUser) {
		
		return authenticationService.login(authUser);
		
	}
	
	@PostMapping("/forgot-password")
	@ApiOperation("Forgot pssword verification")
	public Response forgotPassword(@RequestParam String email) {
		
		return authenticationService.forgotPassword(email);
				
	}
	
	@PostMapping("/change-password")
	@ApiOperation("Change the password")
    public String changePassword(@RequestBody AuthUser authUser) {
		
		return authenticationService.changePassword(authUser.getUsername(), authUser.getPassword());
				
	}

}
