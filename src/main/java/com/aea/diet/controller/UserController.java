package com.aea.diet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aea.diet.exception.InvalidUserException;
import com.aea.diet.model.Batch;
import com.aea.diet.model.Challenger;
import com.aea.diet.model.DietGroup;
import com.aea.diet.model.User;
import com.aea.diet.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("")
	@ApiOperation("Get the details of a user")
	public User getUser(@RequestParam String email) {
		
		return userService.getUser(email);
		
	}
	
	@PostMapping("/refer-challenger")
	@ApiOperation("Refer new challenger for Diet Management System")
	public String referChallenger(@RequestParam String sender, @RequestParam String receiver){
		
		return userService.referChallenger(sender,receiver);
		
	}
	
	
	@GetMapping("/requests")
	@ApiOperation("Get all the pending joining requests")
	public List<Challenger> getRequests(@RequestParam String email) throws InvalidUserException{
		
		return userService.getRequests(email);
		
	}
	
	@GetMapping("/batches")
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
	public String createGroup(@RequestBody DietGroup[] dietGroups,  @RequestParam String email) throws InvalidUserException {
		
		return userService.createGroup(dietGroups, email);
		
	}
	
	@PostMapping("/donate")
	@ApiOperation("Donate for the organization")
	public String donate(@RequestParam String donor, @RequestParam String amount){
		
		return userService.donate(donor,amount);
		
	}
	
	@PutMapping("/status/{status}")
	@ApiOperation("Update the user accept/reject applications")
	public String updateStatus(@PathVariable String status, @RequestParam String email, @RequestBody Challenger challenger) throws InvalidUserException{
		
		return userService.updateStatus(status, email, challenger);
		
	}

}
