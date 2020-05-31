package com.aea.diet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aea.diet.model.Batch;
import com.aea.diet.model.Challenger;
import com.aea.diet.model.DietGroup;
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
	
	@GetMapping("/baches")
	@ApiOperation("Get all the batch details")
	public List<Batch> getBatches(@RequestParam String email) throws InvalidUserException{
		
		return userService.getBatches(email);
		
	}
	
	@GetMapping("/groups")
	@ApiOperation("Get all the group details")
	public List<DietGroup> getGroups(@RequestParam String email) throws InvalidUserException{
		
		return userService.getGroups(email);
		
	}
	
	@PostMapping("/create-group")
	@ApiOperation("Create a new group under existing batch")
	public String createGroup(@RequestBody DietGroup dietGroup,  @RequestParam String email) throws InvalidUserException {
		
		return userService.createGroup(dietGroup, email);
		
	}

}
