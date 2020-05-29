package com.aea.diet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aea.diet.model.Challenger;
import com.aea.diet.service.UserService;
import com.aea.diet.service.exception.InvalidUserException;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/requests")
	@ApiOperation("Get all the pending joining requests")
	public List<Challenger> getRequests(@RequestParam String email) throws InvalidUserException{
		
		return userService.getRequests(email);
		
	}

}
