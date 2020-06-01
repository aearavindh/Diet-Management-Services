package com.aea.diet.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
	
	@Id
	private String email;
	private String role;
	private String name;
	@JsonIgnore
	private String password;
	private String groupName;
	private String batchName;
	private String referralCode;
	private String code;
	private String mobile;
	
	public User() {
		super();
	}

	public User(String email, String role, String name, String password, String groupName, String batchName,
			String referralCode, String code, String mobile) {
		super();
		this.email = email;
		this.role = role;
		this.name = name;
		this.password = password;
		this.groupName = groupName;
		this.batchName = batchName;
		this.referralCode = referralCode;
		this.code = code;
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public String getRole() {
		return role;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getGroupName() {
		return groupName;
	}

	public String getBatchName() {
		return batchName;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public String getCode() {
		return code;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	
}
