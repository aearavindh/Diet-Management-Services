package com.aea.diet.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.aea.diet.model.AuthUser;
import com.aea.diet.model.Challenger;
import com.aea.diet.model.Response;
import com.aea.diet.service.AuthenticationService;
import com.aea.diet.service.UserDetailsService;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {
    
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AuthenticationService authenticationService;
	
	@MockBean
	private UserDetailsService userDetailsService;
	
	String exampleChallengerJson = "{\r\n" + 
			"  \"address\": \"string\",\r\n" + 
			"  \"age\": \"string\",\r\n" + 
			"  \"bmi\": \"string\",\r\n" + 
			"  \"city\": \"string\",\r\n" + 
			"  \"country\": \"string\",\r\n" + 
			"  \"dietaryCustom\": \"string\",\r\n" + 
			"  \"dietaryRestrictions\": \"string\",\r\n" + 
			"  \"email\": \"string\",\r\n" + 
			"  \"gender\": \"string\",\r\n" + 
			"  \"height\": \"string\",\r\n" + 
			"  \"medicalConditions\": \"string\",\r\n" + 
			"  \"mobile\": \"string\",\r\n" + 
			"  \"name\": \"string\",\r\n" + 
			"  \"pinCode\": \"string\",\r\n" + 
			"  \"pregnantStatus\": \"string\",\r\n" + 
			"  \"program\": \"string\",\r\n" + 
			"  \"reason\": \"string\",\r\n" + 
			"  \"referralCode\": \"string\",\r\n" + 
			"  \"state\": \"string\",\r\n" + 
			"  \"status\": \"string\",\r\n" + 
			"  \"weight\": \"string\"\r\n" + 
			"}";
	
	String exampleAuthUser =  "{\r\n" + 
			"  \"password\": \"string\",\r\n" + 
			"  \"role\": \"string\",\r\n" + 
			"  \"username\": \"string\"\r\n" + 
			"}";
	
	Response res = new Response("Success", 1234);
	
	@Test
	public void registerTest() throws Exception {
		
		
		when(authenticationService.register(any(Challenger.class))).thenReturn("Created");
		
		RequestBuilder request = MockMvcRequestBuilders
				.post("/auth/register")
				.content(exampleChallengerJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(request).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals("Created", response.getContentAsString());
	}
	
	@Test
	public void loginTest() throws Exception {
		
		
		when(authenticationService.login(any(AuthUser.class))).thenReturn("Success");
		
		RequestBuilder request = MockMvcRequestBuilders
				.post("/auth/login")
				.content(exampleAuthUser)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(request)
				           .andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals("Success", response.getContentAsString());
	}
	
	@Test
	public void forgotPasswordTest() throws Exception {
		
		
		when(authenticationService.forgotPassword(any(String.class))).thenReturn(res);
		
		RequestBuilder request = MockMvcRequestBuilders
				.post("/auth/forgot-password")
				.param("email","ab@cd.com")
				.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(request).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		JSONAssert.assertEquals("{\"message\":\"Success\",\"code\":1234}", response.getContentAsString(), true);
	}
	
	@Test
	public void changePasswordTest() throws Exception {
		
		
		when(authenticationService.changePassword(any(String.class), any(String.class))).thenReturn("Success");
		
		RequestBuilder request = MockMvcRequestBuilders
				.post("/auth/change-password").content(exampleAuthUser)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(request).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals("Success", response.getContentAsString());
	}

}
