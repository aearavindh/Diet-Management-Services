package com.aea.diet.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aea.diet.dao.BatchRepository;
import com.aea.diet.dao.ChallengerRepository;
import com.aea.diet.dao.DonationRepository;
import com.aea.diet.dao.GroupRepository;
import com.aea.diet.dao.UserRepository;
import com.aea.diet.exception.InvalidUserException;
import com.aea.diet.model.Batch;
import com.aea.diet.model.Challenger;
import com.aea.diet.model.DietGroup;
import com.aea.diet.model.Donation;
import com.aea.diet.model.MailRequest;
import com.aea.diet.model.MailResponse;
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
	private EmailService emailService;
	
	public User getUser(String email) {
		
		return userRepository.findByEmail(email);
		
	}
	
	public List<Challenger> getRequests(String email) throws InvalidUserException{
		
		User user = userRepository.findByEmail(email);
		if(user.getRole().equals("Administrator"))
			return challengerRepository.findByStatus();
		else
		    throw new InvalidUserException("Only administrator can get the requests. You are not authorized");
			
	}

	public List<Batch> getBatches(String email) throws InvalidUserException {
		
		User user = userRepository.findByEmail(email);
		if(user.getRole().equals("Administrator"))
		    return batchRepository.findAll();
		else
			throw new InvalidUserException("Only administrator can get the batches. You are not authorized");
		
	}
	
    public List<DietGroup> getGroups(String email) throws InvalidUserException {
		
    	User user = userRepository.findByEmail(email);
		if(user.getRole().equals("Administrator"))
		    return groupRepository.findAll();
		else
			throw new InvalidUserException("Only administrator can get the groups. You are not authorized");
		
	}

	public String createGroup(DietGroup dietGroup, String email) throws InvalidUserException {
		
		User user = userRepository.findByEmail(email);
		if(user.getRole().equals("Administrator")) {
			DietGroup d = groupRepository.findByName(dietGroup.getName());
			if(d != null) {
		       return "Already exists";
			}else {
				groupRepository.save(dietGroup);
				return "Success";
			}
	    }else
		throw new InvalidUserException("Only administrator can create a group. You are not authorized");
		
	}

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

	public String donate(String donor, String amount) {
		
		Donation donation = donationRepository.save(new Donation(new Date(),donor,Integer.parseInt(amount)));
		if(donation != null)
			return "Success";
		else 
			return "Failure";

	}

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
			String code = challenger.getName().substring(0,3);
			
			if(Integer.parseInt(challenger.getBmi().substring(0,2))<25)
				batchName = "BELOW_BMI_25"; 
			else 
				batchName = "ABOVE_BMI_25";
			
			Optional<DietGroup> group = groupRepository.findById(1);
			
			if(group.get().getName().equals("CHENNAI")) {
				groupName = challenger.getCity().toUpperCase();
			}else if(group.get().getName().equals("VEG")) {
				groupName = challenger.getDietaryCustom().toUpperCase();
			}else if(group.get().getName().equals("MALE")) {
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
			
			User user = new User(challenger.getEmail(), "Challenger", challenger.getName(), password, groupName, batchName, challenger.getReferralCode(), code, challenger.getMobile());
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

}
