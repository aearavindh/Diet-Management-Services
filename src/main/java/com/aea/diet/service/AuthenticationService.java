package com.aea.diet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.aea.diet.dao.ChallengerRepository;
import com.aea.diet.dao.UserRepository;
import com.aea.diet.model.AuthUser;
import com.aea.diet.model.Challenger;
import com.aea.diet.model.MailRequest;
import com.aea.diet.model.Response;
import com.aea.diet.model.User;

@Service
public class AuthenticationService {
		
	@Autowired
	private ChallengerRepository challengerRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	
	/* Add the challenger details to the challenger table */

	public String register(Challenger challenger) {
		
		Challenger c = challengerRepository.save(challenger);
		
		if(c != null)
			return "Created";
		else 
			return null;
		
	}
	
	/* Check whether the user credentials are right or wrong */
	
	public String login(AuthUser authUser) {
		
		User user = userRepository.findByEmail(authUser.getUsername());
		
		if(user==null)
			return "Not registered";
		else if(!user.getRole().equals(authUser.getRole()))
			return "Inappropriate user type";
		else {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			if(encoder.matches(authUser.getPassword(), user.getPassword()))
				return "Success";
			else
				return "Wrong Password";
		}
			
	}
	
	/* Check the user details and send the otp for changing the password */

	public Response forgotPassword(String email) {
		
		User user = userRepository.findByEmail(email);
		Response response = null;
		if(user==null) 
			response = new Response("Not registered", 0);
		else {
			int code = 0;
			while(code<1000) 
			code = (int)(Math.random()*10000);
			
			MailRequest req = new MailRequest(user.getName(), user.getEmail(), "One Time Password", "Your One Time Password(OTP) for changing password is "+code);
			emailService.sendEmail(req);
			
			response = new Response("Success",code);
		}
		
		return response;
	    
	}
    
	/* Update the user's password in the user table */
	
	public String changePassword(String email, String newPassword) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if(userRepository.updatePasswordByEmail(email, encoder.encode(newPassword))==1)
			return "Success";
		else 
			return "Failure";
		
	}

}
