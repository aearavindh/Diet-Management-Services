package com.aea.diet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aea.diet.dao.BatchRepository;
import com.aea.diet.dao.ChallengerRepository;
import com.aea.diet.dao.GroupRepository;
import com.aea.diet.dao.UserRepository;
import com.aea.diet.model.Batch;
import com.aea.diet.model.Challenger;
import com.aea.diet.model.DietGroup;
import com.aea.diet.model.User;
import com.aea.diet.service.exception.InvalidUserException;

@Service
public class UserService {
	
	@Autowired
	private ChallengerRepository challengerRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BatchRepository batchRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
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
			throw new InvalidUserException("Only administrator can get the batches. You are not authorized");
		
	}

	public String createGroup(DietGroup dietGroup, String email) throws InvalidUserException {
		
		User user = userRepository.findByEmail(email);
		if(user.getRole().equals("Administrator")) {
		    groupRepository.save(dietGroup);
			return "Success";
	    }else
		throw new InvalidUserException("Only administrator can create a group. You are not authorized");
		
	}

}
