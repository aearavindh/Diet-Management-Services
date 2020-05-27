package com.aea.diet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aea.diet.model.UserPrincipal;
import com.aea.diet.dao.UserRepository;
import com.aea.diet.model.User;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = repo.findByEmail(username);
		if(user==null)
			throw new UsernameNotFoundException("User Not Found");
		return new UserPrincipal(user);
	}

}
