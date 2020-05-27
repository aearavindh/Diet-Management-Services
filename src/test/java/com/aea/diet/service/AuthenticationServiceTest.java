package com.aea.diet.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.aea.diet.dao.ChallengerRepository;
import com.aea.diet.dao.UserRepository;
import com.aea.diet.model.AuthUser;
import com.aea.diet.model.Challenger;
import com.aea.diet.model.MailRequest;
import com.aea.diet.model.MailResponse;
import com.aea.diet.model.Response;
import com.aea.diet.model.User;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {
	
	@InjectMocks
	private AuthenticationService authenticationService;
	
	@Mock
	private EmailService emailService;
	
	@Mock
	private ChallengerRepository challengerRepository;
	
	@Mock
	private UserRepository userRepository;

	Challenger mockChallenger = new Challenger("a","b","c","d","e","f","g","h","i","j","k",
			"l","m","n","o","p","q","r","s","t","u");
	
	User user = new User("a","b","c","$2a$10$JVgaATghSKBNlFqjn1VENu59mX3KZVp2ftpskR1Xm9LwwqZqA2kTq","e","f","g","h");
	
	AuthUser authUser = new AuthUser("a","d","b");
	
	Response res = new Response("Success", 1234);
	
	MailResponse mailResponse = new MailResponse("Success", true);
	
	@Test
	public void registerTest() {
		when(challengerRepository.save(any(Challenger.class))).thenReturn(mockChallenger);
		assertEquals("Created", authenticationService.register(mockChallenger));
	}
	
	@Test
	public void loginTest() {
		when(userRepository.findByEmail(any(String.class))).thenReturn(user);
		assertEquals("Success", authenticationService.login(authUser));
	}
	
	@Test
	public void forgotPasswordTest() {
		when(userRepository.findByEmail(any(String.class))).thenReturn(user);
		when(emailService.sendEmail(any(MailRequest.class))).thenReturn(mailResponse);
		assertEquals(res.getMessage(), authenticationService.forgotPassword("email").getMessage());
	}
	
	@Test
	public void changePasswordTest() {
		when(userRepository.updatePasswordByEmail(any(String.class), any(String.class))).thenReturn(1);
		assertEquals("Success", authenticationService.changePassword("email", "password"));
	}

}
