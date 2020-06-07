package com.aea.diet.service;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aea.diet.dao.BatchRepository;
import com.aea.diet.dao.ChallengerRepository;
import com.aea.diet.dao.ChartRepository;
import com.aea.diet.dao.DonationRepository;
import com.aea.diet.dao.GroupRepository;
import com.aea.diet.dao.LogRepository;
import com.aea.diet.dao.MessageRepository;
import com.aea.diet.dao.PostRepository;
import com.aea.diet.dao.UserRepository;
import com.aea.diet.exception.InvalidUserException;
import com.aea.diet.model.Batch;
import com.aea.diet.model.Challenger;
import com.aea.diet.model.DailyLog;
import com.aea.diet.model.DietGroup;
import com.aea.diet.model.Donation;
import com.aea.diet.model.MailRequest;
import com.aea.diet.model.MailResponse;
import com.aea.diet.model.Message;
import com.aea.diet.model.MonthlyChart;
import com.aea.diet.model.Post;
import com.aea.diet.model.User;

@Service
public class UserService {
	
	@Autowired
	private ChallengerRepository challengerRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BatchRepository batchRepository;
	
	@Autowired
	private DonationRepository donationRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private ChartRepository chartRepository;
	
	@Autowired
	private LogRepository logRepository;
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private EmailService emailService;
	
	/* Get the particular user based on email */
	
	public User getUser(String email) {
		
		return userRepository.findByEmail(email);
		
	}
	
	/* Get all the pending requests by sending the administrator email */
	
	public List<Challenger> getRequests(String email) throws InvalidUserException{
		
		User user = userRepository.findByEmail(email);
		if(user.getRole().equals("Administrator"))
			return challengerRepository.findByStatus();
		else
		    throw new InvalidUserException("Only administrator can get the requests. You are not authorized");
			
	}
	
	/* Get all the batches by sending the administrator email */

	public List<Batch> getBatches(String email) throws InvalidUserException {
		
		User user = userRepository.findByEmail(email);
		if(user.getRole().equals("Administrator"))
		    return batchRepository.findAll();
		else
			throw new InvalidUserException("Only administrator can get the batches. You are not authorized");
		
	}
	
	/* Get all the groups by sending the administrator email */
	
    public List<DietGroup> getGroups(String email) throws InvalidUserException {
		
    	User user = userRepository.findByEmail(email);
		if(user.getRole().equals("Administrator"))
		    return groupRepository.findAll();
		else
			throw new InvalidUserException("Only administrator can get the groups. You are not authorized");
		
	}
    
    /* Create a group by sending group bean and administrator email */

	public String createGroup(DietGroup[] dietGroups, String email) throws InvalidUserException {
		
		User user = userRepository.findByEmail(email);
		DietGroup d = null;
		if(user.getRole().equals("Administrator")) {
			for(int i=0;i<dietGroups.length;i++) {
			d = groupRepository.findByName(dietGroups[i].getName());
			if(d != null && d.getBatch().getId()==dietGroups[i].getBatch().getId()) {
		       return "Already exists";
			}else {
				groupRepository.save(dietGroups[i]);
			}
			}
			return "Success";
	    }else
		throw new InvalidUserException("Only administrator can create a group. You are not authorized");
		
	}
	
	/* Send refer mail to a user by sending the sender and receiver */

	public String referChallenger(String sender, String receiver) {
		
		User user = userRepository.findByEmail(sender);
		
		MailRequest request = new MailRequest("Challenger", receiver, "Diet Management System - Referral Code",
				"You are invited to take part in our diet management program by "+user.getName()+". Your referral code for joining the program is "+user.getCode());
		MailResponse response = emailService.sendEmail(request);
		
		if(response.getStatus())
			return "Success";
		else 
			return "Failure";
		
	}
	
	/* Donate by sending donar and amount */

	public String donate(String donor, String amount) {
		
		Donation donation = donationRepository.save(new Donation(new Date(),donor,Integer.parseInt(amount)));
		if(donation != null)
			return "Success";
		else 
			return "Failure";

	}
	
	/* Update the status of the challenger which administrator sent. If the status is accepted,
	 * create a user in the user table and send acceptance/rejection mail */

	public String updateStatus(String status, String email, Challenger challenger) throws InvalidUserException {
		
		User u = userRepository.findByEmail(email);
		if(!u.getRole().equals("Administrator"))
			throw new InvalidUserException("Only administrator can accept/reject applications. You are not authorized");
		
		
		User v = userRepository.findByEmail(challenger.getEmail());
		
		if(v != null) {
			MailRequest request = new MailRequest(challenger.getName(), challenger.getEmail(), "Application rejected", "Your application is rejected by the administrator because you are already an approved challenger");
			emailService.sendEmail(request);
			return "Success";
		}
			
		
		
		int rowCount = challengerRepository.updateStatusByEmail(challenger.getEmail(), status);
		
		if(rowCount>=1) {
			if(status.equals("accepted")) {
			String password = "Wipro@";
			String groupName = null;
			String batchName = null;
			String code = challenger.getName().substring(0,3).toUpperCase();
			
			if(Integer.parseInt(challenger.getBmi().substring(0,2))<25)
				batchName = "BELOW_BMI_25"; 
			else 
				batchName = "ABOVE_BMI_25";
			
			List<DietGroup> group = groupRepository.findAll();
			
			if(group.get(0).getName().equals("CHENNAI")) {
				groupName = challenger.getCity().toUpperCase();
			}else if(group.get(0).getName().equals("VEG")) {
				groupName = challenger.getDietaryCustom().toUpperCase();
			}else if(group.get(0).getName().equals("MALE")) {
				groupName = challenger.getGender().toUpperCase();
			}
			
			int random = 0;
			
			while(random<100) 
				random = (int)(Math.random()*1000);
			
			password = password+Integer.toString(random);
			random = 0;
			
			while(random<100) 
				random = (int)(Math.random()*1000);
			
			code = code+Integer.toString(random);
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			User user = new User(challenger.getEmail(), "Challenger", challenger.getName(), encoder.encode(password), groupName, batchName, challenger.getReferralCode(), code, challenger.getMobile());
			userRepository.save(user);
			
			MailRequest request = new MailRequest(user.getName(), user.getEmail(), "Application Approved", "Welcome to Diet program "+challenger.getProgram()+". Your application is approved by administrator. Your username is your email id. And the initial password is "+password+". If you want to refer your friends for this program,"
					+ "                                                             please use the referral code "+code);
			emailService.sendEmail(request);
			
			}
			else {
				String message = "We are sorry to say that your application for our diet program has been rejected by the administrator. ";
				
				if(challenger.getPregnantStatus().equals("yes"))
					message = message + "Since you are pregnant you are rejected. ";
				else if(challenger.getDietaryRestrictions().equals("yes"))
					message = message + "The reason is your dietary restrictions. ";
				else if(challenger.getMedicalConditions().equals("yes"))
					message = message + "The reason is your medical conditions. ";
				
				message = message + "Have a great day";
				
				MailRequest request = new MailRequest(challenger.getName(), challenger.getEmail(), "Application Rejected", message);
				emailService.sendEmail(request);
			}
			return "Success";
				
		} else {
			return "Failure";
		}
		
		
	}
	
	/* Get all the users by sending the administrator email */

	public List<User> getAllUsers(String email) throws InvalidUserException {
		
		User user = userRepository.findByEmail(email);
		if(user.getRole().equals("Administrator"))
			return userRepository.findAll();
		else
		    throw new InvalidUserException("Only administrator can get the users. You are not authorized");
		
	}
	
	/* Remove a user from user table and send mail to user by sending the administrator email */

	public String removeUser(String email, String user) throws InvalidUserException {
		
		User u = userRepository.findByEmail(email);
		if(u.getRole().equals("Administrator")){
			int rowCount = userRepository.deleteByEmail(user);
			if(rowCount>=1) {
				MailRequest request = new MailRequest("User", user, "You are removed", "You have removed from the diet program by the administrator");
				emailService.sendEmail(request);
				return "Success";
			}
			else
				return "Failure";
		}
		else
		    throw new InvalidUserException("Only administrator can delete the user. You are not authorized");
		
		
		
	}
	
	/* Modify a user from user table and send mail to user */

	public String modifyUser(String email, User user) {
		
		User u = userRepository.findByEmail(email);
		if(u.getRole().equals("Administrator")){
			int rowCount = userRepository.updateDetailsByEmail(user.getEmail(), user.getRole(), user.getGroupName(), user.getBatchName());
			if(rowCount>=1) {
				MailRequest request = new MailRequest(user.getName(), user.getEmail(), "Details Updated", "Your details has been modified by the administrator. Please login to see the changes");
				emailService.sendEmail(request);
				return "Success";
			}
			else
				return "Failure";
		}else {
			int rowCount = userRepository.updateMobileByEmail(email, user.getMobile());
			if(rowCount>=1){
				MailRequest request = new MailRequest(user.getName(), user.getEmail(), "Details Updated", "Your details updated successfully");
				emailService.sendEmail(request);
				return "Success";
			}
			else
				return "Failure";
		}
	}
	
	/* Add a user to the user table by sending the administrator email */

	public String addUser(String email, User user) throws InvalidUserException {
		
		User u = userRepository.findByEmail(email);
		if(u.getRole().equals("Administrator")) {
			
			String code = user.getName().substring(0,3).toUpperCase();
			String password = "Wipro@";
			
            int random = 0;
			
			while(random<100) 
				random = (int)(Math.random()*1000);
			
			password = password+Integer.toString(random);
			random = 0;
			
			while(random<100) 
				random = (int)(Math.random()*1000);
			
			code = code+Integer.toString(random);
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			
			user.setCode(code);
			user.setPassword(encoder.encode(password));
			user.setReferralCode(u.getCode());
			
			User us = userRepository.save(user);
			
			if(us!=null) {
				MailRequest request = new MailRequest(user.getName(), user.getEmail(), "Diet Program", "You are added as a "+user.getRole()+" to our diet program. your username is your email id."
						+ "  Your initial password is "+password+". Please update your mobile number once you logged in. you can user the code "+code+" to refer your friends to this program");
				emailService.sendEmail(request);
				return "Success";
			}
			return "Failure";
			
		}
		else
		    throw new InvalidUserException("Only administrator can add users. You are not authorized");
		
	}
	
	/* Add a log to the daily_log table. If a log for the current date already available, update it */

	public String updateLog(DailyLog dailyLog) {
		
		DailyLog d = logRepository.findByEmail(dailyLog.getEmail(), dailyLog.getDate());
		if(d!=null) 
			logRepository.delete(d);
		
		logRepository.save(dailyLog);
		
		return "Success";
		
	}
	
	/* Add a char to the monthly_chart table. If a chart for the current month already available, update it */

	public String updateChart(MonthlyChart monthlyChart) {
		
		MonthlyChart m = chartRepository.findByEmail(monthlyChart.getEmail(), monthlyChart.getMonth());
		if(m!=null) 
			chartRepository.delete(m);
		
		chartRepository.save(monthlyChart);
		
		return "Success";
		
	}
	
	/* Get all the daily logs */

	public List<DailyLog> getLogs() {
		
		return logRepository.findAll();
				
	}
	
	/* Get all the monthly charts */

	public List<MonthlyChart> getCharts() {

        return chartRepository.findAll();
		
	}
	
	/* Send message to a particular user */

	public String sendMessageToUser(Message message) {
		User u = userRepository.findByEmail(message.getTo());
		if(u==null)
			return "Invalid user";
		messageRepository.save(message);
		return "Success";
		
	}
	
	/* Send message to a particular batch */

	public String sendMessageToBatch(Message message) {
		
		List<User> users = userRepository.findAll();
		
		Iterator<User> usersIterator = users.iterator();
		 
		
		if(message.getTo().equals("Challengers")) {
			while(usersIterator.hasNext()) {
			    User u = usersIterator.next();
			    if(u.getRole().equals("Challenger")) {
			    	messageRepository.save(new Message(message.getDate(), u.getEmail(), message.getFromUser(), message.getMessage()));
			    }
			}
		}else if(message.getTo().equals("Motivators")) {
			while(usersIterator.hasNext()) {
			    User u = usersIterator.next();
			    if(u.getRole().equals("Motivator")) {
			    	messageRepository.save(new Message(message.getDate(), u.getEmail(), message.getFromUser(), message.getMessage()));
			    }
			}
		}else if(message.getTo().equals("Administrators")) {
			while(usersIterator.hasNext()) {
			    User u = usersIterator.next();
			    if(u.getRole().equals("Administrator")) {
			    	messageRepository.save(new Message(message.getDate(), u.getEmail(), message.getFromUser(), message.getMessage()));
			    }
			}
		}else {
		
		while(usersIterator.hasNext()) {
		    User u = usersIterator.next();
		    if(u.getRole().equals("Challenger") && u.getBatchName().equals(message.getTo())) {
		    	messageRepository.save(new Message(message.getDate(), u.getEmail(), message.getFromUser(), message.getMessage()));
		    }
		}
		}
		
		return "Success";
		
	}
	
	/* Send mail to a particular group */

	public String sendMessageToGroup(Message message, String batchName) {

        List<User> users = userRepository.findAll();
		
		Iterator<User> usersIterator = users.iterator();
		 
		while(usersIterator.hasNext()) {
		    User u = usersIterator.next();
		    if(u.getRole().equals("Challenger") && u.getBatchName().equals(batchName) && u.getGroupName().equals(message.getTo())) {
		    	messageRepository.save(new Message(message.getDate(), u.getEmail(), message.getFromUser(), message.getMessage()));
		    }
		}
		
		return "Success";
		
	}
    
	/* Get all the messages for a particular user */
	
	public List<Message> getMessages(String email) {
		
		return messageRepository.findByTo(email);
		
	}
	
	/* Post the diet/workout plan to a particular batch */

	public String post(MultipartFile file, String data) throws IOException {
		
		String[] d = data.split("/");
		System.out.println(file);
		System.out.println(file.getBytes());
		Post post = new Post(d[0], d[1], d[2], d[3], file.getBytes());
		postRepository.save(post);
		return "Success";
	}
	
	/* Get all the posts for a particular user */

	public List<Post> getPosts(String email) {
		
		User user = userRepository.findByEmail(email);
		
		return postRepository.findByTo(user.getBatchName());
		
	}
	
	/* End the current program by truncating all tables except administrator user and batch table */

	public String endProgram(String email) throws InvalidUserException {
		
		User u = userRepository.findByEmail(email);
		if(u.getRole().equals("Administrator")){
			challengerRepository.deleteAll();
			chartRepository.deleteAll();
			donationRepository.deleteAll();
			groupRepository.deleteAll();
			logRepository.deleteAll();
			messageRepository.deleteAll();
			postRepository.deleteAll();
			userRepository.deleteByRole("Challenger");
			userRepository.deleteByRole("Motivator");
			return "Success";
		}
		else
		    throw new InvalidUserException("Only administrator can end the program. You are not authorized");
		
		
	}
    
	/* Get the report for a particular batch */
	
	public String getReport(String batch) {
		
		List<User> users = userRepository.findByBatchName(batch);
		
		int mCount = 0;
		int cCount = 0;
		
		Iterator<User> usersIterator = users.iterator();
		 
		while(usersIterator.hasNext()) {
		    User u = usersIterator.next();
		    if(u.getRole().equals("Motivator"))
				mCount+=1;
			else if(u.getRole().equals("Challenger"))
				cCount+=1;
		}
		
		List<DietGroup> groups = groupRepository.findAll();
		int groupsCount = 0;
		Iterator<DietGroup> groupsIterator = groups.iterator();
		 
		while(groupsIterator.hasNext()) {
		    DietGroup g = groupsIterator.next();
		    if(g.getBatch().getName().equals(batch))
		    	groupsCount+=1;
		}
		
		return "STATISTICS: Users - "+users.size()+" Motivators - "+mCount+" Challengers - "+cCount+" Groups - "+groupsCount;
		
	}

}
