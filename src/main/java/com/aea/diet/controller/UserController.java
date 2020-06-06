package com.aea.diet.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aea.diet.exception.InvalidUserException;
import com.aea.diet.model.Batch;
import com.aea.diet.model.Challenger;
import com.aea.diet.model.DailyLog;
import com.aea.diet.model.DietGroup;
import com.aea.diet.model.Message;
import com.aea.diet.model.MonthlyChart;
import com.aea.diet.model.Post;
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
	
	@GetMapping("/all-users")
	@ApiOperation("Get the details of all users")
	public List<User> getAllUsers(@RequestParam String email) throws InvalidUserException {
		
		return userService.getAllUsers(email);
		
	}
	
	@PostMapping("/add-user")
	@ApiOperation("Add a user to the program")
	public String addUser(@RequestParam String email, @RequestBody User user) throws InvalidUserException {
		
		return userService.addUser(email, user);
		
	}
	
	@DeleteMapping("/remove-user")
	@ApiOperation("Remove a particular user from the program")
	public String removeUser(@RequestParam String email, @RequestParam String user) throws InvalidUserException {
		
		return userService.removeUser(email, user);
		
	}
	
	@PutMapping("/modify-user")
	@ApiOperation("Modify a user details")
	public String modifyUser(@RequestParam String email, @RequestBody User user) throws InvalidUserException {
		
		return userService.modifyUser(email, user);
		
	}
	
	@GetMapping("/refer-challenger")
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
	
	@GetMapping("/donate")
	@ApiOperation("Donate for the organization")
	public String donate(@RequestParam String donor, @RequestParam String amount){
		
		return userService.donate(donor,amount);
		
	}
	
	@PutMapping("/status/{status}")
	@ApiOperation("Update the user accept/reject applications")
	public String updateStatus(@PathVariable String status, @RequestParam String email, @RequestBody Challenger challenger) throws InvalidUserException{
		
		return userService.updateStatus(status, email, challenger);
		
	}
	
	@PostMapping("/update-log")
	@ApiOperation("Update the daily logs")
	public String updateLog(@RequestBody DailyLog dailyLog) {
		
		return userService.updateLog(dailyLog);
		
	}
	
	@PostMapping("/update-chart")
	@ApiOperation("Update the monthly charts")
	public String updateChart(@RequestBody MonthlyChart monthlyChart) {
		
		return userService.updateChart(monthlyChart);
		
	}
	
	@GetMapping("/logs")
	@ApiOperation("Get all the daily logs")
	public List<DailyLog> getLogs() {
		
		return userService.getLogs();
		
	}
	
	@GetMapping("/charts")
	@ApiOperation("Get all the monthly charts")
	public List<MonthlyChart> getCharts() {
		
		return userService.getCharts();
		
	}
	
	@PostMapping("/message-to-user")
	@ApiOperation("Send message to user")
	public String sendMessageToUser(@RequestBody Message message) {
		
		return userService.sendMessageToUser(message);
		
	}
	
	@PostMapping("/message-to-batch")
	@ApiOperation("Send message to a batch")
	public String sendMessageToBatch(@RequestBody Message message) {
		
		return userService.sendMessageToBatch(message);
		
	}
	
	@PostMapping("/post")
	@ApiOperation("Post file and message to a group")
	public String post(@RequestParam("plan") MultipartFile file, @RequestParam String data) throws IOException {
		
		return userService.post(file, data);
		
	}
	
	@GetMapping("/posts")
	@ApiOperation("Get all the posts to a particular user")
	public List<Post> getPosts(@RequestParam String email) {
		
		return userService.getPosts(email);
		
	}
	
	@GetMapping("/report/{batch}")
	@ApiOperation("Get the report for a particular batch")
	public String getReport(@PathVariable String batch) {
		
		return userService.getReport(batch);
		
	}
	
	@DeleteMapping("/end-program")
	@ApiOperation("Delete all details from all tables except administrator user data and batch repository")
	public String endProgram(@RequestParam String email) throws InvalidUserException {
		
		return userService.endProgram(email);
		
	}
	

}
